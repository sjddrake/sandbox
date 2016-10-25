package uk.co.neo9.utilities.misc;

import java.util.List;

public interface UtilitiesGridContainerCellWrapperFactoryI {

	public abstract UtilitiesGridContainerCellWrapperI wrapCellModel(Object cellModel);
	public abstract List<UtilitiesGridContainerCellWrapperI> wrapCellModels(List<Object> cellModels);

}
