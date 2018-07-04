package fr.uga.pddl4j.parser;

public class TypedSymbolValue 
{
	TypedSymbol ts;
	Object value;
	Object matchedPredicate;
	
	public TypedSymbolValue() 
	{
		super();
	}
	
	public TypedSymbolValue( TypedSymbolValue other )
	{
		this.ts = new TypedSymbol( other.getTs() );
		this.value = other.getValue();
		this.matchedPredicate = other.getMatchedPredicate();
	}
	
	public TypedSymbolValue(TypedSymbol ts, Object value, Object matchedPredicate ) {
		super();
		this.ts = ts;
		this.value = value;
		this.matchedPredicate = matchedPredicate;
	}

	public Object getMatchedPredicate()
	{
		return matchedPredicate;
	}


	public void setMatchedPredicate(Object matchedPredicate)
	{
		this.matchedPredicate = matchedPredicate;
	}


	public TypedSymbol getTs() 
	{
		return ts;
	}
	
	public void setTs(TypedSymbol ts) 
	{
		this.ts = ts;
	}
	
	public Object getValue() 
	{
		return value;
	}
	
	public void setValue(Object value) 
	{
		this.value = value;
	}
}
