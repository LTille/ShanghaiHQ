package fyp.tingli.manager;


import java.util.ArrayList;
import java.util.List;

import fyp.tingli.model.F1DynFlight;

public interface FlightMgr {
	public List<F1DynFlight> getAllData();
	
	public List<F1DynFlight> findFlight(String flightNo);
	
	public ArrayList getFrmFlightListById(String fid);
}
