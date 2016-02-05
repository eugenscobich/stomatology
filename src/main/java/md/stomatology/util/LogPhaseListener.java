package md.stomatology.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogPhaseListener implements PhaseListener {
	 
	private static final long serialVersionUID = 1L;

	private static Logger LOG = LoggerFactory.getLogger(LogPhaseListener.class);
	
    public long startTime;
 
    
    public void afterPhase(PhaseEvent event) {
        if (event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
            long endTime = System.nanoTime();
            long diffMs = (long) ((endTime - startTime) * 0.000001);
            if (LOG.isDebugEnabled()) {
            	LOG.debug("Execution Time = " + diffMs + "ms");
            }
        }
        LOG.debug("Executed Phase " + event.getPhaseId());
    }
 
    public void beforePhase(PhaseEvent event) {
        if (event.getPhaseId() == PhaseId.RESTORE_VIEW) {
            startTime = System.nanoTime();
        }
    }
 
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
 
}