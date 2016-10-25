package uk.co.neo9.apps.accounts.db.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "BREAKDOWN" )
public class Breakdown {

	private final static String FIELD_NAME_ID = "id";
	private final static String FIELD_NAME_DESCRIPTION = "description";
	private final static String FIELD_NAME_OWNER = "owner";	
	private final static String FIELD_NAME_AMOUNT = "amount";
	private final static String FIELD_NAME_CAT_MAIN = "mainCatId";
	private final static String FIELD_NAME_CAT_SUB = "subCatId";
	
    @Id
    @Column(name = "ID")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    private Long id;	
    
	@Column(name = "OWNER")
    private String owner;
    
	@Column(name = "AMOUNT")
    private Integer amount;
	
	@Column(name = "MAIN_CAT")
    private Long mainCategoryId;
	
	@Column(name = "SUB_CAT")
    private Long subCategoryId;
    
    @Column(name = "DESCRIPTION")
	private String description;
	
	
//	@ManyToOne
//	@JoinColumn(name="id")
//	private Payment payment;
	
    
  	public Breakdown() {
  		super();
  	}    
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Long getMainCategoryId() {
		return mainCategoryId;
	}

	public void setMainCategoryId(Long mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
   
	
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
                append(FIELD_NAME_ID, id).
                append(FIELD_NAME_DESCRIPTION, description).
                append(FIELD_NAME_AMOUNT, amount).
                append(FIELD_NAME_OWNER, owner).
                append(FIELD_NAME_CAT_MAIN, mainCategoryId).
                append(FIELD_NAME_CAT_SUB, subCategoryId).
        		toString();
    }

//	public Payment getPayment() {
//		return payment;
//	}
//
//	public void setPayment(Payment payment) {
//		this.payment = payment;
//	}
	
	
}
