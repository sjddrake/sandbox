package uk.co.neo9.apps.accounts.db.hibernate.old;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table( name = "STATEMENT" )
public class Statement {

    private Long id;
    
	public Statement() {
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
    
    
	
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
                append("id", id).
        		toString();
    }
}
