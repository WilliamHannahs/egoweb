package net.sf.egonet.model;

/**
 * Value object representing an item of a drop-down, listbox, or similar widget
 */
public class QuestionOption extends OrderedEntity
{
	private Long studyId;
	private Long questionId;
	private String name;
	private String value;

	public QuestionOption(Long questionId, String name)
	{
		this.questionId = questionId;
		this.name = name;
	}
	
	public String toString() {
		return name == null || name.isEmpty() ? "<unnamed option>" : name;
	}

	public Long   getStudyId() { return studyId; }
	public Long   getQuestionId() { return questionId; }
	public String getName()       { return name;       }
	public void setName(String val)     { this.name       = val; }
	
	public String getValue() { return this.value; }
	public void setValue(String value) { this.value = value; }

	// For Hibernate use only -----------------

	public QuestionOption() {}

	public void setStudyId(Long val) { this.studyId = val; }
	public void setQuestionId(Long val) { this.questionId = val; }
}
