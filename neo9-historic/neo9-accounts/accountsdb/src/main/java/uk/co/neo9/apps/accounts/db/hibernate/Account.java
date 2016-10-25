package uk.co.neo9.apps.accounts.db.hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "ACCOUNT" )
public class Account {
	
	private final static String FIELD_NAME_ID = "id";
	private final static String FIELD_NAME_DESCRIPTION = "description";
	private final static String FIELD_NAME_NUMBER = "number";

    private Long id;
    private String description;
    private String number;
    
	protected Account() {
		super();
	}


	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}
    


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}
	
	
	
   
	
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
                append(FIELD_NAME_ID, id).
                append(FIELD_NAME_NUMBER, number).
                append(FIELD_NAME_DESCRIPTION, description).
        		toString();
    }
}
