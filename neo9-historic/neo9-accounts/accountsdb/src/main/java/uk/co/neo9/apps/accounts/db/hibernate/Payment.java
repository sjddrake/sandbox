package uk.co.neo9.apps.accounts.db.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "PAYMENT" )
public class Payment {

	@Id
    @Column(name = "ID")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    private Long id;	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "PAYMENT_DATE")	
	private Date date;
	
	@Column(name = "AMOUNT")
	private Integer amount;
	
	@Column(name = "DESCRIPTION")
	private String description;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "METHOD_ID")
    private PaymentMethod method;
    
    
//    @OneToMany(mappedBy="PAYMENT", //mappedBy="owner"
//    		   targetEntity=Breakdown.class,
//    	       fetch=FetchType.EAGER)
//    private List<Breakdown> breakdowns = new ArrayList<Breakdown>();    
	
    
    @OneToMany
    @JoinTable(name = "PAYMENT_BREAKDOWN",
    		   joinColumns={ @JoinColumn(name="PAY_ID", referencedColumnName="ID") },
    	      inverseJoinColumns={ @JoinColumn(name="BRKDWN_ID", referencedColumnName="ID", unique=true) })
    private List<Breakdown> breakdowns = new ArrayList<Breakdown>();       
    
    
	public Payment() {
		super();
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}



	public Integer getAmount() {
		return amount;
	}



	public void setAmount(Integer amount) {
		this.amount = amount;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}




	@Transient
	public boolean isNegative() {
		boolean negative = false;
		if (amount != null && amount.intValue() < 0) {
			negative = true;
		}
		return negative;
	}



	public PaymentMethod getMethod() {
		return method;
	}


	public void setMethod(PaymentMethod method) {
		this.method = method;
	}
	


	public List<Breakdown> getBreakdowns() {
		return breakdowns;
	}


	public void setBreakdowns(List<Breakdown> breakdowns) {
		this.breakdowns = breakdowns;
	}
	
	
	
	
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
                append("id", id).
                append("date", date).
                append("amount", amount).
                append("description", description).
                append("method", method).
        		toString();
    }



	public void addBreakdown(Breakdown b) {
		
		breakdowns.add(b);
		
	}
}
