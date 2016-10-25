package uk.co.jmcg.opentournament;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.co.jmcg.opentournament.round.Round;
import uk.co.jmcg.opentournament.schedule.BasicMatch;
import uk.co.jmcg.opentournament.schedule.Match;
import uk.co.jmcg.opentournament.schedule.Pitch;

public class TournamentScheduler {

	private static final String GRID_KEY_DELIMITER = "|";
	
	private Map<String, MatchTimeSlotWrapper> grid = new HashMap<String, MatchTimeSlotWrapper>();
	private int noOfPitches = 0;
	private int noOfMatches = 0;
	
	private String formGridKey(int pitch, int slot) {
		
		// String key = "p"+pitch+GRID_KEY_DELIMITER+"s"+slot;
		
		// String key = "s"+slot+GRID_KEY_DELIMITER+"p"+pitch;
		
		String slotIndexTxt = padIndex(slot);
		String pitchIndexTxt = padIndex(pitch);
		
		String key = "s"+slotIndexTxt+GRID_KEY_DELIMITER+"p"+pitchIndexTxt;
		
		return key;
	}

	
	private String formGridKey(MatchTimeSlotWrapper wrappedMatch) {
		if (wrappedMatch.slotIndex == null || wrappedMatch.pitchIndex == null){
			throw new RuntimeException("undefined index on the wrapper"); //scooby TODO tighten up
		}
		
		String key = formGridKey(wrappedMatch.pitchIndex.intValue(), wrappedMatch.slotIndex.intValue());
		
		return key;
	}	
	
	
	private String padIndex(int index) {
		
		String paddedIndex = null;
		if (index < 10) {
			paddedIndex = "00"+index;
		} else if (index < 100) {
			paddedIndex = "0"+index;
		} else {
			paddedIndex = ""+index;
		}
		
		return paddedIndex;
	}


	
	
	
	public void inflateSchedule(KnockoutTournament tournament){
		
		/**
		 * This one should just read in the schedule as it 
		 * stands and re-inflate the inner grid
		 * 
		 * THIS IS A STEP TOWARDS ENCAPSULATING THE GRID
		 * 
		 *  > the key for this to work is to use SCHEDULED
		 *  > date which means that this should never be
		 *  > changed by the user... only actual start
		 *  > is updated by the user... scheduled date
		 *  > is only changed by the re-schedule method
		 *  > at which point we know it will have a slot
		 *  
		 *  TODO at this point I think the scheduled start can be at any time within the slot, not just the start
		 *  
		 *  DOCUMENT THE CORRECT USAGE OF THE TWO DATE 
		 *  ATTRIBUTES IN THE CLASS JAVA DOC - scooby TODO
		 * 
		 */

		// Scooby TODO this rubbish but need it for later
		//Scooby TODO ... but do we do anything about the empty slot on this pitch for now?
		List<Round> allRounds = tournament.viewAllRounds();
		Round thisIsRubbish = allRounds.get(0); //scooby TODO not good!
		int interMatchInterval = thisIsRubbish.getInterMatchInterval();
		int matchDuration = thisIsRubbish.getMatchDuration();		

		/*
		 * Every match has a scheduled start time and a pitch. The start time
		 * gives the slot for that pitch. With the slot and pitch index, we
		 * can rebuild the grid key
		 * 
		 * ... need to identify slots by the match start time
		 * 
		 * ... therefore need to build up slot times
		 * 
		 * ... again this could be part of the encapsulated grid
		 */
		
		// get the slot times for the tournament
		Date firstSlotStartTime = tournament.getDate(); //TODO shoudln't really be using this as the start time for the first match!
		List<SlotTimes> slotTimes = buildSlotTimesForTournament(firstSlotStartTime, 
																tournament.getNoOfMatches(),
																tournament.getPitches().size(),
																interMatchInterval,
																matchDuration);
		
		
		// now use the slot times to get the slot index for each match
		// so that it can be wrapped and put into the grid
		List<Pitch> pitches = tournament.getPitches();
		List<Match> allMatches = tournament.getAllMatches();
		for (Iterator<Match> iterator = allMatches.iterator(); iterator.hasNext();) {
			Match match = (Match) iterator.next();
			SlotTimes slot = getSlotTimesForMatch(slotTimes, match);
			if (slot == null) {
				//TODO need to tighten this up!
				throw new RuntimeException("Unable to allocate a slot to the match");
			}
			
			int pitchIndex = getPitchIndexForMatch(pitches, match);
			if (pitchIndex == -1L) {
				//TODO need to tighten this up!
				throw new RuntimeException("Unable to get the pitch index from the match");
			}
			
			
			// now wrap the match and add it to the grid
			String key = formGridKey(pitchIndex, slot.index);
			MatchTimeSlotWrapper wrapper = new MatchTimeSlotWrapper(match);
			wrapper.pitchIndex = new Integer(pitchIndex);
			wrapper.slotIndex = new Integer(slot.index);
			wrapper.slotStartTime = slot.startTime;
			wrapper.slotEndTime = slot.endTime;
			
			grid.put(key, wrapper);
		}
		
		
		// setup the linking
		int noOfPitches = tournament.getPitches().size();
		linkMatchSlots(noOfPitches);
		
		
	}

	
	
	
	protected int getPitchIndexForMatch(List<Pitch> pitches, Match match) {
		int index = -1;
		Long allocatedPitchId = match.getPitch().getId();
		
		// sanity check on the data
		if (allocatedPitchId == null) {
			//TODO need to tighten this up!
			throw new RuntimeException("Undefined Id on pitch");
		}
		
		for (Iterator<Pitch> iterator = pitches.iterator(); iterator.hasNext();) {
			Pitch pitch = iterator.next();
			index++;
			if (pitch.getId() == allocatedPitchId) {
				break; // TODO scooby - this relies on the pitch ordering to be correct and persistence could muck that about
			}
		}
		
		return index;
	}

	protected int getSlotIndexForMatch(List<SlotTimes> slotTimes, Match match) {
		int slotIndex = -1;

		for (Iterator<SlotTimes> iterator = slotTimes.iterator(); iterator.hasNext();) {
			SlotTimes slot = (SlotTimes) iterator.next();
			if (slot.isTimeInSlot(match)) {
				slotIndex = slot.index;
			}
		}
		
		return slotIndex;
	}

	
	protected SlotTimes getSlotTimesForMatch(List<SlotTimes> slotTimes, Match match) {
		SlotTimes slot = null;

		for (Iterator<SlotTimes> iterator = slotTimes.iterator(); iterator.hasNext();) {
			slot = (SlotTimes) iterator.next();
			if (slot.isTimeInSlot(match)) {
				break;
			}
		}
		
		return slot;
	}
	
	
	protected List<SlotTimes> buildSlotTimesForTournament(Date firstSlotStartTime, int noOfMatches, int noOfPitches, int interMatchInterval, int matchDuration) {
		
		// produce a rough estimate for the number of slots needed
		int minimumNoOfSlots = noOfMatches / noOfPitches;
		int noOfSlotsToUse = minimumNoOfSlots + 8; // add on some extra slots for match overruns
		List<SlotTimes> slotTimes = new ArrayList<SlotTimes>(noOfSlotsToUse);
		
		
		
		
		Date thisSlotStartTime = firstSlotStartTime;
		Date nextSlotStartTime = calculateNextMatchTime(thisSlotStartTime,interMatchInterval,matchDuration);
		
		
		int slotIndex = 0;
		do {

			// create this slot's times
			SlotTimes slot = new SlotTimes();
			slot.index = slotIndex;
			slot.startTime = thisSlotStartTime;
			slot.endTime = nextSlotStartTime;
			
			slotTimes.add(slot);
			
			// reset for next iteration
			slotIndex++;
			thisSlotStartTime = nextSlotStartTime;
			nextSlotStartTime = calculateNextMatchTime(thisSlotStartTime,interMatchInterval,matchDuration);
			
		} while (slotIndex < noOfSlotsToUse+1); 

		
		return slotTimes;
	}
	
	
	
	

	public void setupSchedule(KnockoutTournament tournament){
		
		Date firstSlotStartTime = tournament.getDate(); //TODO shoudln't really be using this as the start time for the first mathc!
		List<Pitch> pitches = tournament.getPitches();

		SchedulingProcessJotter jotter = new SchedulingProcessJotter();
		jotter.slotIndex = 0;
		jotter.pitchIndex = 0;
		jotter.startTime = firstSlotStartTime;


		// setup the grid of matches assigned to pitches in a loose 'timeslot'
		// this gives a first pass attempt at setting the start times too
		List<Round> allRounds = tournament.viewAllRounds();
		for (Iterator<Round> iterator = allRounds.iterator(); iterator.hasNext();) {
			Round round = (Round) iterator.next();
			wrapMatchesForARound(round,pitches,jotter);
		}
		
		
		// Scooby TODO this rubbish but need it for later
		//Scooby TODO ... but do we do anything about the empty slot on this pitch for now?
		Round thisIsRubbish = allRounds.get(0); //scooby TODO not good!
		int interMatchInterval = thisIsRubbish.getInterMatchInterval();
		int matchDuration = thisIsRubbish.getMatchDuration();
		
		
		
		
		/**
		 * this is where it starts to go wrong... already I'm taking into account slipped
		 * times... I need to separate the setting up of the data grid from the timings
		 * ... just do an adjustment after... so I need to understand how to build the
		 * data graph without effecting previous scheduling. ** here now **
		 */
		
		
		
		// need a second pass to smooth out any impossible timings due to match
		// feeder dependencies
		List<String> allKeys = getAllGridKeys();
		for (String key : allKeys) {
			
			MatchTimeSlotWrapper wrappedMatch = grid.get(key);
			
			// DEBUG
			if (wrappedMatch.match.getName().equals("Final Match #1")) {
				@SuppressWarnings("unused")
				int scooby = 0;
			}
			// DEBUG
			
			Date feederEndTime = getFeederMatchesEndTime(wrappedMatch); // scooby TODO trouble here is that the feeder end time is calculate by the match to a slightly different algortihm
			Date matchStartTime = wrappedMatch.match.getScheduledStartTime();
			boolean feederMatchesEndAfterScheduledStart = feederEndTime.after(matchStartTime);
			while (feederMatchesEndAfterScheduledStart) {
			//if (feederEndTime.after(matchStartTime)) {
				// modify the scheduled start time
//THis was actually moving it up an extra slot				
//				Date newStartTime = calculateNextMatchTime(feederEndDate,
//														   interMatchInterval,
//														   matchDuration);
//				wrappedMatch.match.setScheduledStartTime(newStartTime); 
				
				
/*				
				wrappedMatch.match.setScheduledStartTime(feederEndTime); //SCOOBY TODO built in assumption that match can start at end of previous slot
				// SCOOBY TODO can't use the end time directly... need to get the next slot start time, really
				
				//SCooby TODO this monkeys with the strict slot approach
				wrappedMatch.slotStartTime = feederEndTime;
				Date slotNewEndTime = calculateNextMatchTime(feederEndTime,
															 interMatchInterval,
															 matchDuration);
				wrappedMatch.slotEndTime = slotNewEndTime;
				//... may have got the times correct but the grid-indexing is now out!
*/
				
				// SCOOBY TODO revisit
				// So, need to find next available slot... we know that the current slot
				// for the match can't be used due to feeder dependency so try the next one
				Date newStartTime = calculateNextMatchTime(matchStartTime,
														   interMatchInterval,
														   matchDuration);
				
				if (feederEndTime.before(newStartTime)) {
					feederMatchesEndAfterScheduledStart = false;
					wrappedMatch.match.setScheduledStartTime(newStartTime); 
					wrappedMatch.slotStartTime = newStartTime;
					Date slotNewEndTime = calculateNextMatchTime(newStartTime,
																 interMatchInterval,
																 matchDuration);
					wrappedMatch.slotEndTime = slotNewEndTime;
				} else {
					matchStartTime = newStartTime;
				}
				
				
			}
			
		}
		
		
	}	
	
	
	protected void dumpGridKeyVsWrapper(){
		
		List<String> allKeys = getAllGridKeys();
		for (String key : allKeys) {
			
			MatchTimeSlotWrapper wrappedMatch = grid.get(key);
			
			String checkKey = formGridKey(wrappedMatch);
			String message = "key = "+key+" / wrapper = "+checkKey;
			System.out.println(message+" / same = "+checkKey.equals(key));

		}
		
	}
	
	
	
	
	protected List<String> getAllGridKeys() {
		List<String> allKeys = new ArrayList<String>();
		allKeys.addAll(grid.keySet());
		Collections.sort(allKeys);
		return allKeys;
	}
	

	private void wrapMatchesForARound(Round round, List<Pitch> pitches, SchedulingProcessJotter jotter){

		// the matches are in the rounds
		List<Match> matchesInRound = round.getMatches();
		int numberOfPitches = pitches.size();
		
		// build the map of the matches assigned to pitch
		int slotIndex = jotter.slotIndex;
		int pitchIndex = jotter.pitchIndex;
		Date thisMatchStartTime = jotter.startTime;
		Date nextMatchStartTime = calculateNextMatchTime(thisMatchStartTime,round.getInterMatchInterval(),round.getMatchDuration());
		
		for (Iterator<Match> iterator = matchesInRound.iterator(); iterator.hasNext();) {
			
			Match match = (Match) iterator.next();
			
			// set the match pitch
			match.setPitch(pitches.get(pitchIndex));
			//TODO is this needed setMatchTimingsFromRound(firstRound,match);
			match.setScheduledStartTime(thisMatchStartTime);

			
			// assign each match to a pitch-slot on the grid
			String key = formGridKey(pitchIndex, slotIndex);
			MatchTimeSlotWrapper wrapper = new MatchTimeSlotWrapper(match);
			wrapper.pitchIndex = new Integer(pitchIndex);
			wrapper.slotIndex = new Integer(slotIndex);
			wrapper.slotStartTime = thisMatchStartTime;
			wrapper.slotEndTime = nextMatchStartTime;
			
			
			grid.put(key, wrapper);
			// reset the indexes if required
			if (pitchIndex == numberOfPitches-1) {
				pitchIndex = 0;
				slotIndex++;
				thisMatchStartTime = nextMatchStartTime;
				nextMatchStartTime = calculateNextMatchTime(nextMatchStartTime,round.getInterMatchInterval(),round.getMatchDuration());
			} else {
				pitchIndex++;
			}
			
		}
		
		// record where the loops got to so that the processing of the next round can continue
		jotter.pitchIndex = pitchIndex;
		jotter.slotIndex = slotIndex;
		jotter.startTime = thisMatchStartTime;
	}
	
	
	
	/*
	 * Taken from SImon's code in the ROund Class
	 */
	private Date calculateNextMatchTime(Date currentMatchTime, int interMatchInterval, int matchDuration)
	{
		Calendar retVal = Calendar.getInstance();
		
		retVal.setTime(currentMatchTime);
		
		int interval = (0 == interMatchInterval) ? 5 : interMatchInterval;
		retVal.add(Calendar.MINUTE, matchDuration + interval);
		
		return retVal.getTime();
		
	}
	
	private List<Match> extractMatchesFromRounds(List<Match> matches, Round round) {
		
		// get the matches from this round
		List<Match> roundMatches = round.getMatches();
		if (roundMatches != null && roundMatches.size() > 0) {
			matches.addAll(roundMatches);
		}
		
		// get the matches from child rounds
		List<Round> children = round.getChildRounds();
		if (children != null && children.size() > 0) {
			
			for (Iterator<Round> iterator = children.iterator(); iterator.hasNext();) {
				Round childRound = (Round) iterator.next();
				matches = extractMatchesFromRounds(matches,childRound);
			}
		}
		
		return matches;
	}

	

	public String exportPitchSchedule(){
		
		//TODO THIS WORKS WITH THE 'Init' APPROACH AND REQUIRES CACHING OF STATE
		
		StringBuffer buff = new StringBuffer();
		
		int slot = 1;
		int pitch = 1;
		boolean notDone = true;
		for (int i = 0; i < noOfMatches; i++) {
			// assign each match to a pitch-slot on the grid
			String key = formGridKey(pitch, slot);
			MatchTimeSlotWrapper wrappedMatch = grid.get(key);
			Match match = wrappedMatch.match;
			String outputLine = buildPitchScheduleOutput(pitch, match); // ?? use a decorator to make use of the grid traversing code?
			buff.append(outputLine);
			// buff.append(System.getProperty("line.separator")); //TODO want to centralise this
			// reset the indexes if required
			if (pitch == noOfPitches) {
				pitch = 1;
				slot++;
			} else {
				pitch++;
			}
			
		}		
		
		return buff.toString();
		
	}
	
	
	
	public String exportPitchSchedule(Tournament tourny){
		
		//TODO dont want to be passing in the Tournament!!!!
		
		int numberOfPitches = tourny.getPitches().size();
		
		//TODO THIS WORKS WITH THE 'SetupSchedule' APPROACH AND NEEDS TO BETTER KNOW ITS OWN BOUNDS!!!!
		
		StringBuffer buff = new StringBuffer();
		
//		int slot = 0;
//		int pitch = 0;
//		boolean notDone = true;
//		while (notDone) {
//			// retrieve each match from a pitch-slot on the grid
//			String key = formGridKey(pitch, slot);
//			MatchTimeSlotWrapper wrappedMatch = grid.get(key);
//			
//			if (wrappedMatch != null) {
//				Match match = wrappedMatch.match;
//				String outputLine = buildPitchScheduleOutput(pitch, match); // ?? use a decorator to make use of the grid traversing code?
//				buff.append(outputLine);
//				// buff.append(System.getProperty("line.separator")); //TODO want to centralise this
//				// reset the indexes if required
//				if (pitch == numberOfPitches - 1) {
//					pitch = 0;
//					slot++;
//				} else {
//					pitch++;
//				}
//				
//			} else {
//				notDone = false;
//			}
//			
//		}		
		
		List<String> allKeys = getAllGridKeys();
		for (String key : allKeys) {
			MatchTimeSlotWrapper wrappedMatch = grid.get(key);
			
			if (wrappedMatch != null) {
				Match match = wrappedMatch.match;
				String outputLine = buildPitchScheduleOutput(wrappedMatch, match); // ?? use a decorator to make use of the grid traversing code?
				buff.append(outputLine);
				// buff.append(System.getProperty("line.separator")); //TODO want to centralise this
				// reset the indexes if required

			}
			
		}
		
		
		return buff.toString();
		
	}
	

	
	
	
	
	public String exportPitchGrid(Tournament tourny){
		
		//TODO dont want to be passing in the Tournament!!!!
		
		int numberOfPitches = tourny.getPitches().size();

		List<StringBuffer> rows = new ArrayList<StringBuffer>(numberOfPitches);
		for (int i = 0; i < numberOfPitches; i++) {
			int pitchNo = i+1;
			StringBuffer line = new StringBuffer("Pitch "+pitchNo+",");
			rows.add(line);
		}
		
		
		
		//TODO THIS WORKS WITH THE 'SetupSchedule' APPROACH AND NEEDS TO BETTER KNOW ITS OWN BOUNDS!!!!
		
//		StringBuffer buff = new StringBuffer();
		StringBuffer slotTimesBuff = new StringBuffer(",");
		

		
		
		
		Integer slotIndex = new Integer(-1);
		Integer pitchIndex = new Integer(0);

		
		
		List<String> allKeys = getAllGridKeys();
		for (String key : allKeys) {
			
			// get the wrapped match and, if its in this slot, 
			MatchTimeSlotWrapper wrappedMatch = grid.get(key);
			//DEBUG
			if (wrappedMatch.slotIndex == null) {
				@SuppressWarnings("unused")
				int x = 1;
			}
			//DEBUG
			if ((wrappedMatch.slotIndex.equals(slotIndex) == false)) {
				slotTimesBuff.append(TournamentExporter.formatTime(wrappedMatch.slotStartTime));
				slotTimesBuff.append(",");
				slotIndex = wrappedMatch.slotIndex;
				pitchIndex = new Integer(0);
			}
			
			
			// get the buffer that stores the text for this line
			StringBuffer line = rows.get(pitchIndex);
			
			if (wrappedMatch.isEmptySlot == false && wrappedMatch.match != null) {
				Match match = wrappedMatch.match;
				line.append(match.getName());
				line.append(" - ");
				line.append(TournamentExporter.formatTime(wrappedMatch.match.getScheduledStartTime()));
				line.append(",");
				//line.append(System.getProperty("line.separator")); //TODO want to centralise this
				// reset the indexes if required

			} else {
				
				line.append("empty slot,");
			}
			
			pitchIndex++;
			
		}
		
		
		// put it all together
		StringBuffer buff = new StringBuffer();
		buff.append(slotTimesBuff);
		buff.append(System.getProperty("line.separator")); //TODO want to centralise this
		for (Iterator<StringBuffer> iterator = rows.iterator(); iterator.hasNext();) {
			StringBuffer row = (StringBuffer) iterator.next();
			buff.append(row);
			buff.append(System.getProperty("line.separator")); //TODO want to centralise this
		}
		
		return buff.toString();
		
	}	
	
	
	
	
	
	
	private String buildPitchScheduleOutput(int pitchIndex, Match match) {

//		TODO get rid of this version
		
		
		StringBuffer buff = new StringBuffer();
		buff.append(TournamentExporter.buildPitchGridLine(pitchIndex+1, match));
		
		return buff.toString();
	}
	
	private String buildPitchScheduleOutput(MatchTimeSlotWrapper wrappedMatch, Match match) {

//		StringBuffer buff = new StringBuffer();
//		buff.append(pitch);
//		buff.append(",");
//		buff.append(match.getName());
//		buff.append(",");
//		buff.append(match.getScheduledStartTime());

//		return buff.toString();
		
		SimpleDateFormat TIME_HH_MM = new SimpleDateFormat("HH:mm");
		
		StringBuffer buff = new StringBuffer();
		buff.append(TIME_HH_MM.format(wrappedMatch.slotStartTime));
		buff.append(",");
		buff.append(TIME_HH_MM.format(wrappedMatch.slotEndTime));
		buff.append(",");	
		buff.append(TournamentExporter.buildPitchGridLine(wrappedMatch.pitchIndex+1, match));
		
		return buff.toString();
	}


	public int getNoOfPitches() {
		return noOfPitches;
	}


	public void setNoOfPitches(int noOfPitches) {
		this.noOfPitches = noOfPitches;
	}

/*
 * GOING A BIT OFF THE TRACK HERE
 * 
	public void compressSchedule() {

		// iterate over time-slots and validate start times & pitches
		
		
		// for every match in a slot, check:
		// > end time of feeder matches for that match
		// > end time of previous match on that pitch
	
	
		// process a slot against its previous slot
//		List<Match> previousSlotMatches = getSlotMatches(1);
//		List<Match> currentSlotMatches = getSlotMatches(2);

		// algorithm
		//		
		// Remembering that each pitch has an allocated match in a time-slot		
		// 
		// For every match in all slots other than the first slot,
		// check the start time against:
		// > end time of feeder matches for that match
		// > end time of previous match on that pitch
		//
		// These checks will highlight:
		// > pitch time slots that are available because of dependencies
		// > matches that are waiting to be played but have a blocked pitch
		// 
		// ... with this info, we should be able to re-allocate
		
		int timeSlotIndex = 2;
		List<MatchTimeSlotWrapper> slotMatches = getSlotMatches(timeSlotIndex);
		int pitchIndex = 1;
		for (Iterator<MatchTimeSlotWrapper> iterator = slotMatches.iterator(); iterator.hasNext();) {
			MatchTimeSlotWrapper wrappedMatch = (MatchTimeSlotWrapper) iterator.next();
			List<MatchTimeSlotWrapper> previousSlotMatches = getPreviousPitchSlots(pitchIndex,timeSlotIndex);
			
			// get the most recent previous slot
			int mostRecentIndex = previousSlotMatches.size()-1;
			MatchTimeSlotWrapper previousMatchSlot = previousSlotMatches.get(mostRecentIndex);
			
			// check it's status
			// Match previousMatch = previousMatchSlot.match;
			boolean hasSlotMatchOverrun = checkForSlotOverrun(previousMatchSlot);
			wrappedMatch.pitchIsStillInUse = hasSlotMatchOverrun;
			
			// check the feeder matches
			boolean hasFeederMatch1Overrun = checkForFeederMatchOverrun(wrappedMatch,1);
			boolean hasFeederMatch2Overrun = checkForFeederMatchOverrun(wrappedMatch,2);
			if (hasFeederMatch1Overrun || hasFeederMatch2Overrun) {
				 wrappedMatch.feederMatchesNotComplete = true;
			}
		}
		
	}
*/
	
	

	public void reschedule(Tournament tournament) {
		
		// Scooby TODO naff!!
		List<Round> allRounds = tournament.viewAllRounds();
		Round thisIsRubbish = allRounds.get(0); //scooby TODO not good!
		int interMatchInterval = thisIsRubbish.getInterMatchInterval();
		int matchDuration = thisIsRubbish.getMatchDuration();
		// Scooby TODO naff!!

		List<String> allKeys = getAllGridKeys();

		
		// re-schedule due to dependencies and slippages
		boolean iterationCausedChanges = true;
		while (iterationCausedChanges) {
			iterationCausedChanges = checkDependencies( allKeys, 
														interMatchInterval,
														matchDuration);
		}
		
		
		// compress the schedule as the algorithm may have introduced empty pitch slots
		compressSchedule(interMatchInterval,matchDuration);
		
		
		/*
		Add the pitch no to the wrapper now
		we can loop around using all indexes and for each wrapped match we can get the time for that pitch
		we then check each start time for the same pitch to validate it against previous
		... we then mark that wrapper if it needs to be re-scheduled
		....... not sure how we then act on it????
		*/
		
		// as the game times may have changed again due to pitch clashes, we need to check feeder game dependency again
		
				
//		// SO THIS IS ITERATIVE LIKE A SORT UNTIL NO MORE CHANGE IS REQUIRED	
//		
		
		// !!!!!!!!!! turns out I can't simply drop back in
		
//		allKeys = getAllGridKeys(); // the keys will have changed when schedule was compressed
//		iterationCausedChanges = true;
//		while (iterationCausedChanges) {
//			iterationCausedChanges = checkDependencies( allKeys, 
//														interMatchInterval,
//														matchDuration);
//		}
		
		
		// tempted to try and do it in one pass, combinging the pitch check with the original feeder match check
		// but I don't think that would allow us to spot holes in the schedule that we could fill
				
	}	

	
	private void compressSchedule(int interMatchInterval, int matchDuration) {
	
		// first of all identify if any slots have become free...
		// to do this we need the games per pitch
		List<String> allKeys = getAllGridKeys();
		Map<Integer,List<MatchTimeSlotWrapper>> pitchMatchesGrid = new HashMap<Integer, List<MatchTimeSlotWrapper>>();
		
		for (String key : allKeys) {
			
			MatchTimeSlotWrapper wrappedMatch = grid.get(key);
			
			// DEBUG
//			if (wrappedMatch.match.getName().equals("Semi final Match #2")) {
//				@SuppressWarnings("unused")
//				int scooby = 0;
//			}
			// DEBUG
			
			
			// get the List of matches on this pitch
			List<MatchTimeSlotWrapper> pitchMatches = pitchMatchesGrid.get(wrappedMatch.pitchIndex);
			if (pitchMatches == null) {
				pitchMatches = new ArrayList<MatchTimeSlotWrapper>();
				pitchMatchesGrid.put(wrappedMatch.pitchIndex, pitchMatches);
			}
			
			// add the match to the list
			pitchMatches.add(wrappedMatch);
			
		}
		
		
		
		// now process all the matches on a pitch
		// ... check for slipped matches on a pitch across its slots
		// ... insert empty match/pitch instances for any empty slot
		Set<Integer> pitchKeys = pitchMatchesGrid.keySet();
		List<List<MatchTimeSlotWrapper>> reIndexedSlots = new ArrayList<List<MatchTimeSlotWrapper>>();
		int maxNoOfSlots = 0;
		List<MatchTimeSlotWrapper> completeTimeSlotExample = null;
		for (Integer pitchKey : pitchKeys) {
			List<MatchTimeSlotWrapper> pitchMatches = pitchMatchesGrid.get(pitchKey);
			List<MatchTimeSlotWrapper> reIndexedMatches = reIndexMatchSlotsForPitch(pitchMatches);
			int noOfSlotsOnPitch = reIndexedMatches.size();
			if (noOfSlotsOnPitch > maxNoOfSlots) {
				maxNoOfSlots = noOfSlotsOnPitch;
				completeTimeSlotExample = reIndexedMatches;
			}
			reIndexedSlots.add(reIndexedMatches);
		}
	
		// the introduction of empty match/pitch slots means that the no of time slots per pitch
		// is now uneven... so even them up again
		int pitchIndex = 0;
		for (Iterator<List<MatchTimeSlotWrapper>> iterator = reIndexedSlots.iterator(); iterator.hasNext();) {
			List<MatchTimeSlotWrapper> reIndexedPitchMatches = (List<MatchTimeSlotWrapper>) iterator.next();
			// int missingSlots = maxNoOfSlots - reIndexedPitchMatches.size();
			if ( maxNoOfSlots - reIndexedPitchMatches.size() > 0) {
				for (int i = reIndexedPitchMatches.size(); i < maxNoOfSlots; i++) {
					// get a corresponding pitch/match slot from another pitch 
					// in order to copy over the slot's times to a new, empty 
					// slot for this pitch
					MatchTimeSlotWrapper wrappedPitchSlot = completeTimeSlotExample.get(i);
					//reIndexedPitchMatches.add(createEmptySlot(pitchIndex)); 
					MatchTimeSlotWrapper emptySlot = createEmptySlot(wrappedPitchSlot); // just using this for convenience
					emptySlot.pitchIndex = pitchIndex;
					reIndexedPitchMatches.add(emptySlot);
				}
			}
			pitchIndex++;
		}
		
		
		// the matches/slots have been re-indexed according to game time
		// so the grid must now be re-built to reflect any movement
		this.grid.clear();
		int slotIndex = 0;
		pitchIndex = 0;
		for (Iterator<List<MatchTimeSlotWrapper>> iterator = reIndexedSlots.iterator(); iterator.hasNext();) {
			List<MatchTimeSlotWrapper> matchesOnPitch = (List<MatchTimeSlotWrapper>) iterator.next();
			for (Iterator<MatchTimeSlotWrapper> iterator2 = matchesOnPitch.iterator(); iterator2.hasNext();) {
				MatchTimeSlotWrapper wrappedMatch = (MatchTimeSlotWrapper) iterator2.next();
				String key = formGridKey(pitchIndex, slotIndex);
				
				//scooby TODO - will the key be reflected by the wrapper??
				String checkKey = formGridKey(wrappedMatch);
				if (checkKey.equals(key) == false) {
					String message = "key = "+key+" but wrapper = "+checkKey;
					System.out.println(message);
	//				throw new RuntimeException("wrapper and grid are out of sync: "+message);
				}
				//scooby TODO - will the key be reflected by the wrapper??
				
				
				//scooby TODO - reseting wrpper version of the keys
				wrappedMatch.slotIndex = new Integer(slotIndex);
				wrappedMatch.pitchIndex = new Integer(pitchIndex);
				//scooby TODO - reseting wrpper version of the keys
				
				
				this.grid.put(key, wrappedMatch);
				slotIndex++;
			}
			slotIndex = 0;
			pitchIndex++;
		}
		
		
		// now try and identify matches that can be moved to empty pitch time-slots
		allKeys = getAllGridKeys();
		List<List<MatchTimeSlotWrapper>> allSlots = new ArrayList<List<MatchTimeSlotWrapper>>();
		List<MatchTimeSlotWrapper> slotMatches = null;
		
		for (String key : allKeys) {
			
			MatchTimeSlotWrapper wrappedMatch = grid.get(key);
			
			// the slot starts at pitch zero, so initialise for the loop 
			// every time we hit pitch zero
			if (key.endsWith("p000")) {
				slotMatches = new ArrayList<MatchTimeSlotWrapper>();
				allSlots.add(slotMatches);
			}
			
			// add the match to the list
			slotMatches.add(wrappedMatch);
			
		}		
		
		// check through the time-slots for ones that have empty pitches
		boolean slotHasIncompleteMatches = false;
		List<MatchTimeSlotWrapper> currentSlotMatches = null;
		List<MatchTimeSlotWrapper> previousSlotMatches = null;
		for (Iterator<List<MatchTimeSlotWrapper>> iterator = allSlots.iterator(); iterator.hasNext();) {
			
			previousSlotMatches = currentSlotMatches;
			currentSlotMatches = (List<MatchTimeSlotWrapper>) iterator.next();
			
			if (slotHasIncompleteMatches == false) {
				// test every slot for incomplete matches until we find one... 
				// ... no point testing after that as subsequent slots will always have unplayed matches
				slotHasIncompleteMatches = determineIncompleteMatches(currentSlotMatches);
			}
		
			
			if (slotHasIncompleteMatches) {
				
				// check if previous slot has any free pitches
				boolean previousSlotHasEmptyPiches = determineEmptyPitches(previousSlotMatches);
				
				if (previousSlotHasEmptyPiches) {
					// this algorithm assumes that a game in the current slot will not find
					// a free slot earlier than the previous slot
					tryMovingMatchesToPreviousSlot(previousSlotMatches,currentSlotMatches,
													interMatchInterval,matchDuration);
				}
				
				
				// could get the situation now of having no matches in the current slot - could have all moved
				// - SCOOBY don't actually think we should have do anything special... though it does
				// mean any empty pitches left in previous slot wont be considered for a move TODO
				
				
			}
		}
		
	}



	private void tryMovingMatchesToPreviousSlot(List<MatchTimeSlotWrapper> previousSlotMatches,
												List<MatchTimeSlotWrapper> currentSlotMatches,
												int interMatchInterval, int matchDuration) {

		// get the free pitches in the previous slot 
		List<MatchTimeSlotWrapper> freePitchSlots = new ArrayList<MatchTimeSlotWrapper>();
		for (Iterator<MatchTimeSlotWrapper> iterator = previousSlotMatches.iterator(); iterator.hasNext();) {
			MatchTimeSlotWrapper wrappedMatch = (MatchTimeSlotWrapper) iterator.next();
			if (wrappedMatch.isEmptySlot) {
				freePitchSlots.add(wrappedMatch);
			}
		}
		
		if (freePitchSlots.size() == 0) {
			// TODO raise exception as this should never happen
		}
		
		// now see if there are any matches that can be moved to that time slot
		// Date slotStartTime = freePitchSlots.get(0).slotStartTime;
		Date slotEndTime = freePitchSlots.get(0).slotEndTime;
		List<MatchTimeSlotWrapper> moveableMatches = new ArrayList<MatchTimeSlotWrapper>();
		for (Iterator<MatchTimeSlotWrapper> iterator = currentSlotMatches.iterator(); iterator.hasNext();) {
			MatchTimeSlotWrapper currentSlotWrappedMatch = (MatchTimeSlotWrapper) iterator.next();

			
			boolean isCandidateToMove = testMatchFeedersAgainstStartTime(currentSlotWrappedMatch,
																			slotEndTime,
																			interMatchInterval,
																			matchDuration);
			if (isCandidateToMove) {
				moveableMatches.add(currentSlotWrappedMatch);
			}
			
		}
		
		
		// narrowed down the focus, so now pick the ones to move and do the moving
//		if (moveableMatches.size() > 0) {
//			System.out.println("got some! "+moveableMatches.size());
//			for (Iterator<MatchTimeSlotWrapper> iterator = moveableMatches.iterator(); iterator.hasNext();) {
//				MatchTimeSlotWrapper moveableMatch = (MatchTimeSlotWrapper) iterator.next();
//				System.out.println(TournamentExporter.buildPitchGridLine(moveableMatch.pitchIndex, moveableMatch.match));
//			}
//			System.out.println("got some! "+moveableMatches.size());
//		}
		
		int noOfMoveableMatches = moveableMatches.size();
		if (noOfMoveableMatches > 0) {

			// order the matches by the start time so that the 
			// latest ones can be moved first
			MatchTimeSlotWrapperComparator comparator = new MatchTimeSlotWrapperComparator();
			comparator.comparisonTypeFeederMatchEndTime = true;
			Collections.sort(moveableMatches,comparator);
			
			
//			for (Iterator<MatchTimeSlotWrapper> iterator = moveableMatches.iterator(); iterator.hasNext();) {
//				MatchTimeSlotWrapper moveableMatch = (MatchTimeSlotWrapper) iterator.next();
//				
//				here - should have list of matches to be moved and a list of free slots
//				so all I have to do is assign matches in order (reverse order??) until
//				I run out of free-slots
//				
//			}

			int index = noOfMoveableMatches-1;
			for (Iterator<MatchTimeSlotWrapper> iterator = freePitchSlots.iterator(); iterator.hasNext();) {
				
				if (index > -1) {
					MatchTimeSlotWrapper freeSlot = (MatchTimeSlotWrapper) iterator.next();
					MatchTimeSlotWrapper matchToMove = moveableMatches.get(index);
					moveMatchToPitchSlot(freeSlot,matchToMove,interMatchInterval,matchDuration);					
				} else {
					break; // used up all the free slots
				}
				index--;
			}
			
		}
		
	}

	
	private void moveMatchToPitchSlot(MatchTimeSlotWrapper freeSlot,
									  MatchTimeSlotWrapper matchToMove,
									  int interMatchInterval, int matchDuration) {

		// assign the match to the free-slot
		freeSlot.match = matchToMove.match;
		freeSlot.match.setPitch(freeSlot.pitch);
		freeSlot.isEmptySlot = false;
		
		// update the match's start time
		BasicMatch match = (BasicMatch)freeSlot.match;
		Date feederEndTime = match.getFeederMatchesEndTime(); //Scooby TODO setup the start time according to the rules
		Date newMatchStartTime = calculateNextMatchTime(feederEndTime,interMatchInterval,matchDuration);
		match.setScheduledStartTime(newMatchStartTime);
		
		// turn the match's old slot into a freeSlot
		matchToMove.isEmptySlot = true;
		matchToMove.match = null;
		
	}

	private boolean testMatchFeedersAgainstStartTime(MatchTimeSlotWrapper wrappedMatch, Date testTime,
			int interMatchInterval, int matchDuration) {
		
		boolean isCandidateToMove = false;
		
		// safety
		if (wrappedMatch.isEmptySlot) {
			return isCandidateToMove;
		}
		
		// determine the match's earliest possible start time from the
		// end time of its feeder matches
		Date feederEndTime = getFeederMatchesEndTime(wrappedMatch);
		Date thresholdTime = testTime; // scooby - prime example of parameterisation & tune the algol so we dont move stupidly
		if (feederEndTime.before(thresholdTime)) {
			isCandidateToMove = true;
		}
		return isCandidateToMove;
	}

	private boolean determineIncompleteMatches(List<MatchTimeSlotWrapper> wrappedMatches) {
		
		boolean hasIncompleteMatches = false;
		for (Iterator<MatchTimeSlotWrapper> iterator2 = wrappedMatches.iterator(); iterator2.hasNext();) {
			
			MatchTimeSlotWrapper wrappedMatch = (MatchTimeSlotWrapper) iterator2.next();
			if (wrappedMatch.isEmptySlot == false && wrappedMatch.match.isPlayed() == false) {
				hasIncompleteMatches = true;
			}

		}
		
		return hasIncompleteMatches;
	}
	
	
	private boolean determineEmptyPitches(List<MatchTimeSlotWrapper> wrappedMatches) {
		
		boolean hasEmptySlots = false;
		if (wrappedMatches != null && wrappedMatches.size() > 0) {
			for (Iterator<MatchTimeSlotWrapper> iterator2 = wrappedMatches
					.iterator(); iterator2.hasNext();) {
				MatchTimeSlotWrapper wrappedMatch = (MatchTimeSlotWrapper) iterator2
						.next();
				if (wrappedMatch.isEmptySlot) {
					hasEmptySlots = true;
				}
			}
		}
		return hasEmptySlots;
	}

	private List<MatchTimeSlotWrapper> reIndexMatchSlotsForPitch(List<MatchTimeSlotWrapper> pitchMatches) {
		
		// the match assigned to a pitch slot may well be delayed sufficiently
		// enough to push it out of that slot and into the next
		int noOfSlots = pitchMatches.size();
		List<MatchTimeSlotWrapper> reIndexedSlots = new ArrayList<MatchTimeSlotWrapper>();
		for (int i = 0; i < noOfSlots; i++) {
			
			// find the length of availability between subsequent matches on the pitch
			// and if there is a gap longer than a game's length, insert an empty slot instance
			
			
			MatchTimeSlotWrapper wrappedMatch = (MatchTimeSlotWrapper) pitchMatches.get(i);
			
			if (wrappedMatch.match.isPlayed()) {
				// don't need to check played matches
				reIndexedSlots.add(wrappedMatch);
				
			} else {
	
				if (wrappedMatch.isMatchInTimeSlot() == false) {
					// this slot's match has moved beyond the edge of the time slot
					// so it is effectively in the next time slot. In that case, put
					// a new empty time slot in this position
					reIndexedSlots.add(createEmptySlot(wrappedMatch));

					
					//scooby TODO this is an untested change
					wrappedMatch.slotIndex++;
				}
				
				reIndexedSlots.add(wrappedMatch);
				
			}
		}
		
		return reIndexedSlots;
	}

	
	
	protected void linkMatchSlots(int noOfPitches) {
		
		
//		List<MatchTimeSlotWrapper> previousSlotMatches = getSlotMatches(0);
//		List<MatchTimeSlotWrapper> currentSlotMatches = getSlotMatches(1);
//		
//		
//		for (int i = 0; i < previousSlotMatches.size(); i++) {
//			MatchTimeSlotWrapper previousMatchOnPitch = previousSlotMatches.get(i);
//			MatchTimeSlotWrapper currentMatchOnPitch = currentSlotMatches.get(i);
//			currentMatchOnPitch.linkToPreviousSlot(previousMatchOnPitch);
//		}
		
		
		int currentSlotIndex = 1; // NOTE!!!
		int pitchIndex = 0;
		
		//List<String> allKeys = getAllGridKeys();
		//for (String key : allKeys) {
		boolean notDone = true;
		boolean matchHasBeenLinkedInThisSlot = false;
		do {
			
			// get the wrapped match for the current pitch time slot
			String currentKey = formGridKey(pitchIndex, currentSlotIndex);
			MatchTimeSlotWrapper currentWrappedMatch = grid.get(currentKey);
			String previousKey = formGridKey(pitchIndex, currentSlotIndex-1);
			MatchTimeSlotWrapper previousWrappedMatch = grid.get(previousKey);			

			// link the two, if they exist!
			if (currentWrappedMatch != null && previousWrappedMatch != null) {
				currentWrappedMatch.linkToPreviousSlot(previousWrappedMatch);
				matchHasBeenLinkedInThisSlot = true;
			}
			
			// using the pitch index to control the indexes over the iterations
			if (pitchIndex == noOfPitches-1) {
				// have completed a 'timeslot' so reset the iterators
				pitchIndex = 0;
				currentSlotIndex++;
				// check for the while loops boundary
				if (matchHasBeenLinkedInThisSlot == false) {
					// must have run out of matches!
					notDone = false;
				} else {
					matchHasBeenLinkedInThisSlot = false;
				}
			}
			pitchIndex++;
			
			
		} while (notDone); // SCOOBY TODO dodgy!!
		
		
	}
	
	
	
	private MatchTimeSlotWrapper createEmptySlot(MatchTimeSlotWrapper wrappedMatch) {
		MatchTimeSlotWrapper emptyWrapper = new MatchTimeSlotWrapper(null);
		emptyWrapper.isEmptySlot = true;
		//if (wrappedMatch != null) {
			emptyWrapper.slotIndex = wrappedMatch.slotIndex; // scooby TODO will this always be the case?
			emptyWrapper.pitchIndex = wrappedMatch.pitchIndex;
			emptyWrapper.slotStartTime = wrappedMatch.slotStartTime;
			emptyWrapper.slotEndTime = wrappedMatch.slotEndTime;
			emptyWrapper.pitch = wrappedMatch.pitch;
			
		//}
		return emptyWrapper;
	}

	private MatchTimeSlotWrapper createEmptySlot(int pitchIndex) {
		MatchTimeSlotWrapper emptyWrapper = new MatchTimeSlotWrapper(null);
		emptyWrapper.isEmptySlot = true;
		emptyWrapper.pitchIndex = pitchIndex;
		return emptyWrapper;
	}	
	
	private boolean checkDependencies(List<String> keys, int interMatchInterval, int matchDuration){
		
		boolean checkCausedChanges = false;
		checkCausedChanges = checkFeederMatchDependencies(keys,interMatchInterval,matchDuration);

		if (checkCausedChanges) {
			// now that the original times have slipped, the pitch slots may need changing
			checkCausedChanges = checkPitchDependencies(keys, interMatchInterval, matchDuration);
		}
		return checkCausedChanges;
	}

	
	
	private boolean checkFeederMatchDependencies(List<String> keys, int interMatchInterval, int matchDuration){
		
		boolean hasScheduleChanged = false;
		
		for (String key : keys) {
			
			MatchTimeSlotWrapper wrappedMatch = grid.get(key);
			
			// DEBUG
			if (wrappedMatch == null || wrappedMatch.match == null) {
				@SuppressWarnings("unused")
				int scooby = 0;
			} else if ("Quarter Final Match #4".equals(wrappedMatch.match.getName())){
				@SuppressWarnings("unused")
				int scooby = 0;
			}
			// DEBUG
			
			// ignore played matches... don't need to reschedule those
			// ... and empty slots!
			if (wrappedMatch.isEmptySlot == false && wrappedMatch.match.isPlayed() == false) {
				Date feederEndTime = getFeederMatchesEndTime(wrappedMatch);
				
				
//scooby				Here I am - this algorithm works great for catching the slippages, but what if a game has ended early
//				or heaven forbid cancellations!!! Anyway... the compress changes aren't effecting the timings because 
//				of this assuming it moves out not in.... having said that, perhaps I ought to work out the new time
//				when I swop the pitch over and not rely on this code here... still a problem for now-show matches!
//				.... or are no-shows a 'nice-to-have'???
				
				if (feederEndTime.after(wrappedMatch.match.getScheduledStartTime())) {
				// if (feederEndDate.compareTo(wrappedMatch.match.getScheduledStartTime()) != 0) {  ---> Hah! Infinite loop!
					hasScheduleChanged = true;
					// modify the scheduled start time
					//Scooby TODO ... but do we do anything about the empty slot on this pitch for now?
					Date newStartTime = calculateNextMatchTime(feederEndTime,
							interMatchInterval,
							matchDuration);
					wrappedMatch.match.setScheduledStartTime(newStartTime);
				}
			}
			
		}	
		
		return hasScheduleChanged;
	}
	
	
	
	private boolean checkPitchDependencies(List<String> keys, int interMatchInterval, int matchDuration){
		
		boolean hasScheduleChanged = false;
		
		// temporary map to hold previous pitch game
		Map<Integer,MatchTimeSlotWrapper> previousPitchGames = new HashMap<Integer, MatchTimeSlotWrapper>();
		
		for (String key : keys) {
			
			MatchTimeSlotWrapper wrappedMatch = grid.get(key);
			
			// DEBUG
//			if (wrappedMatch.match.getName().equals("Semi final Match #2")) {
//				@SuppressWarnings("unused")
//				int scooby = 0;
//			}
			// DEBUG
			
			
			// get previous match on the pitch
			MatchTimeSlotWrapper previousWrappedMatch = previousPitchGames.get(wrappedMatch.pitchIndex);
			
			
			

			//if (wrappedMatch.match.isPlayed() == false) { ... not sure if we can ignore played matches
			if (previousWrappedMatch != null) {
			
				Date previousMatchEndTime = previousWrappedMatch.match.getScheduledEndTime(); // This is really ACTUAL end time
				if (previousMatchEndTime.after(wrappedMatch.match.getScheduledStartTime())) {
					hasScheduleChanged =true;
					// modify the scheduled start time
					//Scooby TODO ... but do we do anything about the empty slot on this pitch for now?
					Date newStartTime = calculateNextMatchTime(previousMatchEndTime,
							interMatchInterval,
							matchDuration);
					wrappedMatch.match.setScheduledStartTime(newStartTime);
				}
			}
			
			//}

			// store the match for the next iteration
			previousPitchGames.put(wrappedMatch.pitchIndex,wrappedMatch);
		}	
		
		
		return hasScheduleChanged;
	}	
	
	
	
	private Date getFeederMatchesEndTime(MatchTimeSlotWrapper wrappedMatch) {

		// feeder matches are defined against BasicMatch
		BasicMatch basicMatch = (BasicMatch)wrappedMatch.match;
		Date latestDate = basicMatch.getFeederMatchesEndTime();
		
		return latestDate;
	}
	
	
	
	private boolean checkForFeederMatchOverrun(MatchTimeSlotWrapper wrappedMatch, int feederIndex) {

		// get a handle to the feeder match and, if its null, stop there
		Match feederMatch = null;
		BasicMatch basicMatch = (BasicMatch)wrappedMatch.match;
		if (feederIndex == 1) {
			feederMatch = basicMatch.getTeam1FeederMatch();
		} else if (feederIndex == 2) {
			feederMatch = basicMatch.getTeam2FeederMatch();
		} else {
			// TODO raise exception
		}
		
		if (feederMatch == null) {
			return false;
		}
		
		
		//TODO scooby - so how do I do this then?!
		
		boolean hasOverrun = false;
		String matchName = feederMatch.getName();
		if (matchName.equalsIgnoreCase("Knockout Round 1 Match #5")) {
			hasOverrun = true;
		}
		
		return hasOverrun;
	}


//	private boolean checkForSlotOverrun(MatchTimeSlotWrapper previousMatchSlot) {
//		boolean hasOverrun = false;
//		
//		//TODO scooby - so how do I do this then?!
//		
//		String matchName = previousMatchSlot.match.getName();
//		if (matchName.equalsIgnoreCase("Knockout Round 1 Match #5")) {
//			hasOverrun = true;
//		}
//		
//		return hasOverrun;
//	}


	/**
	 * Gets the wrapped matches for the given pitch prior to the given timeslot - does NOT
	 * include the given timeslot. The wrappedMatches are returned in order, the earliest
	 * timeslot first, etc.
	 * 
	 * 
	 * @param pitchIndex
	 * @param timeSlotIndex
	 * @return
	 */
	private List<MatchTimeSlotWrapper> getPreviousPitchSlots(int pitchIndex, int currentSlotIndex) {

		List<MatchTimeSlotWrapper> slots =  new ArrayList<MatchTimeSlotWrapper>(); 
		
		// build the keys for the given pitch and prior slots and get the wrapped matches
		for (int i = 1; i < currentSlotIndex; i++) {
			String key = formGridKey(pitchIndex, i);
			MatchTimeSlotWrapper wrappedMatch = grid.get(key);
			slots.add(wrappedMatch);
		}
		
		return slots;
	}


	private List<MatchTimeSlotWrapper> getSlotMatches(int slotIndex) {
		List<MatchTimeSlotWrapper> matches = new ArrayList<MatchTimeSlotWrapper>();
		
		for (int i = 0; i < noOfPitches; i++) {
			String key = formGridKey(i+1, slotIndex);
			MatchTimeSlotWrapper wrappedMatch = grid.get(key);
			// Match match = wrappedMatch.match;
			matches.add(wrappedMatch);
		}
		
		return matches;
	}
	
	
	private class SchedulingProcessJotter {
		
		Date startTime;
		int slotIndex;
		int pitchIndex;
	}
	
	
	
	private class MatchTimeSlotWrapper {
		
		public Pitch pitch; // added this at the last because of assigning matches to empty pitch slots... maybe a lookup instead?
		boolean isEmptySlot = false;
		Date slotStartTime;
		Date slotEndTime; //scooby NOT SURE about this just cos there are so many dates... but this is a reference point
		Integer pitchIndex; // could have used pitchId but this may prove easier as its the key in the map
		Integer slotIndex;
		Match match;
		
		MatchTimeSlotWrapper previousSlotForPitch = null;
		MatchTimeSlotWrapper nextSlotForPitch = null;
		
		
//		boolean pitchIsStillInUse = false;
//		boolean feederMatchesNotComplete = false;
	
		boolean matchHasSlipped = false;
		@SuppressWarnings("unused")
		Date expectedFinishTime;
		
		public MatchTimeSlotWrapper(Match wrapThisMatch){
			match = wrapThisMatch;
			if (wrapThisMatch != null) {
				pitch = wrapThisMatch.getPitch(); // scooby - NOTE!!!
				// determineExpectedFinishTime();
			}
		}

//		private void determineExpectedFinishTime() {
//			// get the actual start time from the match if it exists
//			Date actualStart = match.getActualStartTime();
//			
//			// as there is a start time, we have a slippage
//			if (actualStart != null) {
//				matchHasSlipped = true;
//			}
//			
//			if (matchHasSlipped) {
//				// calculate the expected finish time based on the slippage
//				// TODO ... calculation				
//			}
//			else 
//			{
//				// TODO ... not sure if I should default it in	
//				expectedFinishTime = match.getScheduledEndTime();
//			}
//		}
		
		
		
		public void linkToPreviousSlot(MatchTimeSlotWrapper previousMatchOnPitch) {
			// this links time slots to each other so we can easily navigate in time
			this.previousSlotForPitch = previousMatchOnPitch;
			previousMatchOnPitch.nextSlotForPitch = this;
			
			//TODO could enforce that the pitch has to be the same!
			
		}

		public boolean isMatchInTimeSlot() {
			
			boolean isInTimeSlot = true;
			
			if (getMatchStartTime().after(slotEndTime)) {
				isInTimeSlot = false;
			}
			return isInTimeSlot;
		}
		
		
		public final Date getMatchStartTime() {
			
			// scooby  - should this be part of the match functionality
			
			Date startTime = match.getActualStartTime();
			if (startTime == null) {
				startTime = match.getScheduledStartTime();
			}
			
			return startTime;
		}
		
		
	}
	
	
	private class MatchTimeSlotWrapperComparator implements Comparator<MatchTimeSlotWrapper>{

		boolean comparisonTypeFeederMatchEndTime = false;
		
		public int compare(MatchTimeSlotWrapper o1, MatchTimeSlotWrapper o2) {
			
			validateComparisonType();
			
			int result = 0;
			
			if (comparisonTypeFeederMatchEndTime) {
				result = compareByFeederMatchEndTime(o1, o2);
			}
			
			return result;
		}
		
		
		private void validateComparisonType() {
			// scooby TODO expect to have more than one type of comparison so need to 
			// enforce setting the type by raising an exception here!
			
		}


		private int compareByFeederMatchEndTime(MatchTimeSlotWrapper wrappedMatch1, MatchTimeSlotWrapper wrappedMatch2) {
			int result = 0;
			
			BasicMatch basicMatch1 = (BasicMatch)wrappedMatch1.match;
			Date endTime1 = basicMatch1.getFeederMatchesEndTime();
			
			BasicMatch basicMatch2 = (BasicMatch)wrappedMatch1.match;
			Date endTime2 = basicMatch2.getFeederMatchesEndTime();
			
			result = endTime1.compareTo(endTime2);
			
			return result;
		}
		
	}
	
	
	private class SlotTimes {
		
		int index = -1;
		Date startTime = null;
		Date endTime = null;
		
		public boolean isTimeInSlot(Date timeToCheck){
			boolean timeIsInSlot = false;
			
			if (timeToCheck.equals(startTime)) { 
				timeIsInSlot = true;
				
			} else if (timeToCheck.after(startTime) && timeToCheck.before(endTime)) {
				timeIsInSlot = true;
			}
			
			return timeIsInSlot;
		}
		
		
		public boolean isTimeInSlot(Match matchToCheck){
			boolean timeIsInSlot = false;
			
			timeIsInSlot = isTimeInSlot(matchToCheck.getScheduledStartTime());
			
			return timeIsInSlot;
		}
		
	}
}
