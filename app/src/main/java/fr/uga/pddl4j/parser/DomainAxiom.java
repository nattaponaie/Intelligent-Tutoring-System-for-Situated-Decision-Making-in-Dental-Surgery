package fr.uga.pddl4j.parser;

import java.util.List;

public class DomainAxiom  {
	
	
	/*
	 <axiom-def> ::= (:axiom <GD>)
:vars (<typed list (variable)>)
:context <GD>
:implies <literal(term)>)

(:axiom
:vars (?x ?y - physob)
:context (on ?x ?y)
:implies (above ?x ?y)))

	 * */
	
	//1 variable list (name typed list)
	//2 context g
	//3. implies 
	public DomainAxiom( List<TypedSymbol> variables, 
						Exp context, 
						Exp implies) 
	{
		super();
		this.variables = variables;
		this.context = context;
		this.implies = implies;
	}
	
	//This keeps the structure
	List<TypedSymbol> variables;
	Exp context;
	Exp implies;
	
	//this keeps the expression (from (a --> b) to not(A || b)
	Exp implicationNode;		//for keeping expression
	
	public List<TypedSymbol> getVariables() {
		return variables;
	}
	public void setVariables(List<TypedSymbol> variables) {
		this.variables = variables;
	}
	
	public Exp getContext() {
		return context;
	}
	public void setContext(Exp context) {
		this.context = context;
	}
	
	public Exp getImplies() {
		return implies;
	}
	public void setImplies(Exp implies) {
		this.implies = implies;
	}
	
	public Exp getImplicationNode() {
		return implicationNode;
	}
	public void setImplicationNode(Exp implicationNode) {
		this.implicationNode = implicationNode;
	}
	
	@Override
	public boolean equals(final Object object) 
	{
	 if (object != null && object instanceof DomainAxiom ) 
	       {
			 DomainAxiom other = (DomainAxiom) object;
			 return other.getVariables().equals( this.variables )
				&&  other.getContext().equals(this.context)
				&&  other.getImplies().equals(this.implies);
				//&&  other.getImplicationNode().equals(this.implicationNode);
	       }
	       
	       return false;
	}
	
	@Override
    public int hashCode() {
		return this.getVariables().hashCode() +
				this.getContext().hashCode() + 
				this.getImplies().hashCode();
				//this.getImplicationNode().hashCode();
    }
	
	@Override
    public String toString() 
	{
        final StringBuilder str = new StringBuilder();
    	// type list of variables.
    	if (!this.getVariables().isEmpty() )
    	{
    		str.append("(");
	        for (TypedSymbol variable : this.getVariables() ) 
	        {
	            str.append(" ").append(variable.toString());
	        }
	        str.append(")");
    	}
	       
    	//context
    	str.append( this.getContext().toString() );
    	str.append(" ");
    	
    	//implies
    	str.append( this.getImplies().toString() );
    	str.append(" ");
//            str.append( this.implicationNode.toString() );
        return str.toString();
    }
	
	public DomainAxiom copy(){
		DomainAxiom copy = new DomainAxiom( this.variables, this.context, this.implies );
		return copy;
//		return (DomainAxiom) this.clone();
	}
	
	/*
	 public void replaceParams(List<Parameter> oldNames, List<Parameter> newNames){
		precondition.replaceParams(params,newNames);
		effects.replaceParams(params,newNames);
		super.replaceParams(oldNames, newNames);
	}
	 * */
	
	private Exp replaceParamsInExp( Symbol old_s, Symbol new_s, Exp exp )
	{
//		System.out.println( "------- DomainAxiom.replaceParamsInExp() -------" );
//		System.out.println( "[old, new] : [" + old_s +", " + new_s + "]");
//		
//		System.out.println( "old Exp : " + exp );
		
		//Replace atom
		if( exp.getAtom() != null )
		{
			for(Symbol s: exp.getAtom() )
			{
				if(s.getImage().equals( old_s.getImage() ) )
				{
					s.setImage(new_s.getImage() );
					s.setKind(new_s.getKind());
				}
			}
		}
		
		for( Exp child : exp.getChildren() )
		{
//			System.out.println(child);
			for(Symbol si_child: child.getAtom() )
			{
				if(si_child.getImage().equals( old_s.getImage() ) )
				{
					//replace with new image
					si_child.setImage(new_s.getImage() );
					si_child.setKind(new_s.getKind());
				}
			}
			
		}
		
//		System.out.println( "new Exp : " + exp );
		
		return exp;
	}
	
	public void replaceParams(List<TypedSymbol> oldVars, List<TypedSymbol> newVars ) throws Exception
	{
//		System.out.println( oldVars );
//		System.out.println( newVars );
		this.setVariables(newVars);
		
		//System.out.println( "---------------------------------" );
		for(Symbol old_s: oldVars)
		{
			Symbol new_s = newVars.get( oldVars.indexOf(old_s) );
			
			//System.out.println( "[old, new] : [" + old_s +", " + new_s + "]");
			
			//Replace context
			Exp context = this.getContext().getClone( null );
			context = replaceParamsInExp(old_s, new_s, context);
			this.setContext(context);
			
			/*if( context.getAtom() != null )
			{
				for(Symbol s: context.getAtom() )
				{
					if(s.getImage().equals( old_s.getImage() ) )
					{
						s.setImage(new_s.getImage() );
						s.setKind(new_s.getKind());
						
						this.setContext(context);
					}
				}
			}*/
			
//			System.out.println( "new context : " + context );
			
			//Replace implies
			Exp implies = this.getImplies().getClone( null );
			implies =  replaceParamsInExp(old_s, new_s, implies);
			this.setImplies( implies );
			/*for( Exp exp_implies_child : implies.getChildren() )
			{
				System.out.println(exp_implies_child);
				for(Symbol si_child: exp_implies_child.getAtom() )
				{
					if(si_child.getImage().equals( old_s.getImage() ) )
					{
						System.out.println( si_child );
						//replace with new image
						si_child.setImage(new_s.getImage() );
						si_child.setKind(new_s.getKind());
						System.out.println( si_child );
					}
				}
				
				this.setImplies(implies);
			}
			
			System.out.println( "new implies : " + implies );*/
			
		}
		
	}
}
