package fr.uga.pddl4j.parser;

import java.io.Serializable;
import java.util.ArrayList;

//----------------------------------------------------------
//NOTE: This class uses NamedTypedList class as the original
//----------------------------------------------------------

/**
 * This class is used to to parse the domain rules in the pattern of 
 * atomic formula skeleton -> another atomic formula skeleton
 * 
 * NOTE: This class is to deprecate. we use DomainAxiom instead of domain rules.
 */
public class DomainRuleImplication  implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	NamedTypedList fromAtomicSkeleton;
	NamedTypedList toAtomicSkeleton;
	
	Exp implicationNode;
	 /**
     * Creates a named typed list from a specified typed list.
     *
     * @param list the list.
     * @throws NullPointerException if the specified typed list is null.
     */
//    public DomainRuleImplication(final DomainRuleImplication item) {
//        if (item == null) {
//            throw new NullPointerException("list == null");
//        }
//        this.fromAtomicSkeleton = new NamedTypedList( item.getFromAtomic() );
//        this.toAtomicSkeleton = new NamedTypedList( item.getToAtomic() );
//    }
    
    public DomainRuleImplication(final Exp node) {
        if (node  == null) {
            throw new NullPointerException("expression node  == null");
        }
        this.implicationNode = node;
    }
    
    public Exp getImplicationNode() {
		return implicationNode;
	}

	public void setImplicationNode(Exp implicationNode) {
		this.implicationNode = implicationNode;
	}

	/*
    public DomainRuleImplication(final NamedTypedList fAtomic, NamedTypedList tAtomic ) 
    {
        if (fAtomic == null) {
            throw new NullPointerException();
        }
        
        if (tAtomic == null) {
            throw new NullPointerException();
        }
        
        this.fromAtomicSkeleton = fAtomic;
        this.toAtomicSkeleton = tAtomic;
    }

	public NamedTypedList getFromAtomic() {
		return fromAtomicSkeleton;
	}

	public void setFromAtomic(NamedTypedList fromAtomic) {
		this.fromAtomicSkeleton = fromAtomic;
	}

	public NamedTypedList getToAtomic() {
		return toAtomicSkeleton;
	}

	public void setToAtomic(NamedTypedList toAtomic) {
		this.toAtomicSkeleton = toAtomic;
	}
    
    */
    /**
     * Return if this named typed list is equal to another object.
     */
    @Override
    public boolean equals(final Object object) {
        if (object != null && object instanceof DomainRuleImplication ) 
        {
        	DomainRuleImplication other = (DomainRuleImplication) object;
        	return other.getImplicationNode().equals(this.getImplicationNode() );
        }
        
        return false;
    }
    
    
    /**
     * Returns the hash code value of this named typed list.
     */
    @Override
    public int hashCode() {
       // return this.getFromAtomic().hashCode() + this.getToAtomic().hashCode();
    	 return this.getImplicationNode().hashCode();
    }
    
    
    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder();
//        str.append("(");
//        str.append( this.fromAtomicSkeleton.toString() );
//        str.append( " --> "  );
//        str.append( this.toAtomicSkeleton.toString() );
          str.append( this.implicationNode.toString() );
        return str.toString();
    }
}
