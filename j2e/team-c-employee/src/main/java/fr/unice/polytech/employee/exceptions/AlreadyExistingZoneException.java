package fr.unice.polytech.employee.exceptions;

public class AlreadyExistingZoneException extends Exception {
    private String conflictingName;

	public AlreadyExistingZoneException(String name) {
		conflictingName = name;
	}


	public AlreadyExistingZoneException() {
	}

	public String getConflictingName() {
		return conflictingName;
	}

	public void setConflictingName(String conflictingName) {
		this.conflictingName = conflictingName;
	}
}
