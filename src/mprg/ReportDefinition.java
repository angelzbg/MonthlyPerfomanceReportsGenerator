package mprg;

public class ReportDefinition {
	
	private int topPerformersThreshold;
	private boolean useExperienceMultiplier;
	private int periodLimit;
	
	public ReportDefinition(int topPerformersThreshold, boolean useExperienceMultiplier, int periodLimit) {
		this.topPerformersThreshold = topPerformersThreshold;
		this.useExperienceMultiplier = useExperienceMultiplier;
		this.periodLimit = periodLimit;
	}

	public int getTopPerformersThreshold() {
		return topPerformersThreshold;
	}

	public boolean isUseExperienceMultiplier() {
		return useExperienceMultiplier;
	}

	public int getPeriodLimit() {
		return periodLimit;
	}

}