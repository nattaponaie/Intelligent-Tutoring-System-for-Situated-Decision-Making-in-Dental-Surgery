package kb.util;

import com.surgical.decision3.common.bean.datastream.DataStream;
import com.surgical.decision3.common.controller.ApplicationController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.uga.pddl4j.parser.Connective;
import fr.uga.pddl4j.parser.Domain;
import fr.uga.pddl4j.parser.DomainAxiom;
import fr.uga.pddl4j.parser.Exp;
import fr.uga.pddl4j.parser.Op;
import fr.uga.pddl4j.parser.Parser;
import fr.uga.pddl4j.parser.Problem;
import fr.uga.pddl4j.parser.Symbol;
import fr.uga.pddl4j.parser.TypedSymbol;
import kb.common.datastructure.worldstate.State;

import static kb.util.Utils.isAvailableInProjectionEffect;

/**
 * Created by Nattapon on 7/6/2560.
 */

public class InferenceEngineUtil {

    public static Map<String, ArrayList<TypedSymbol>> typeMap = new HashMap<>();
    public static List<DomainAxiom> groundDAList = new ArrayList<DomainAxiom>();

    public static void initializedWorldState(Parser parser ) throws Exception
    {
        List<Exp> initList = parser.getProblem().getInit();

        State s = new State(0); // this is the initial step so we can assign '0' directly
        s.setFactPredicate(initList);
        s.setFactAndEffect(initList);

        ArrayList<State> currentStateList = new ArrayList<State>();
        currentStateList.add(s);

        WorldStateUtil.setWorldStateList(currentStateList);

    }

    public static void transformDomainAxiomToGround(Parser parser ) throws Exception
    {
        ArrayList<DomainAxiom> daList = (ArrayList<DomainAxiom>) parser.getDomain().getDomainAxioms();

        for(DomainAxiom da: daList )
        {
            int x = da.getVariables().size();
            List<TypedSymbol> newVars = new ArrayList<TypedSymbol>();

            computeDomainAxioms( newVars, da, x );
        }
    }

    public static void computeDomainAxioms( List<TypedSymbol> variables, DomainAxiom da, int left) throws Exception
    {
        if (left != 0)
        {
            //Parameter p = action.params.get(action.params.size() - left);
            TypedSymbol da_var = da.getVariables().get( da.getVariables().size() - left );

            //System.out.println( da_var.getImage() );

            if( InferenceEngineUtil.typeMap.containsKey( da_var.getTypes().get(0).getImage() ) )
            {
                ArrayList<TypedSymbol> item_list = InferenceEngineUtil.typeMap.get( da_var.getTypes().get(0).getImage() );
                //System.out.println( "--> items in typeMap : " + item_list.toString() );

                for( TypedSymbol item : item_list )
                {
                    List<TypedSymbol> new_item_list = new ArrayList<TypedSymbol>(variables);
                    new_item_list.add( item );

                    //System.out.println( "--> new_item_list : " + new_item_list.toString() );

                    //Recursive
                    computeDomainAxioms( new_item_list, da, left-1 );
                }
            }
            else
            {
                //System.out.println("No objects of this type " + da_var.getImage() + " was declared.");
                System.exit(0);
            }
        }
        else
        {
            DomainAxiom new_da = da.copy();

            //System.out.println( "--> new da: " +  new_da.toString() );
            new_da.replaceParams( da.getVariables(), variables );
            groundDAList.add( new_da );
        }
//        System.out.println("Ground Domain Axiom");
//        System.out.println(groundDAList.toString());
    }

    public static void transformActionToGround( Parser parser ) throws Exception
    {
        ArrayList<Op> actionList = (ArrayList<Op>) parser.getDomain().getOperators();

        for(Op allActionList : actionList)
        {
            //DONETODO: For initial version only. this needs to change for dealing with many actions
            ArrayList<TypedSymbol> paramList = (ArrayList<TypedSymbol>) allActionList.getParameters();
            //        System.err.println("toey "+ actionList.get(0).getParameters().get(0).getTypes().get(0).getImage());
            ArrayList<TypedSymbol> new_paramList = new ArrayList<TypedSymbol>();

            HashMap<String, String> variableContantMap = new HashMap<>();

            //Parameters
            //System.out.println( "------------- OLD  ------------" );
            for (TypedSymbol ts : paramList) {
                //Manage param (typed Symbol), create new param List.
                //if( typeMap.containsKey( da_var.getTypes().get(0).getImage() ) )
                if (typeMap.containsKey(ts.getTypes().get(0).getImage())) {
                    ArrayList<TypedSymbol> tsList = typeMap.get(ts.getTypes().get(0).getImage());
                    //                System.err.println("toey "+ ts.getTypes().get(0).getImage() +" "+ts.getImage());
                    TypedSymbol constantTs = tsList.get(0); //new
                    //                System.out.println( "old value = " + ts.getImage() );
                    //                System.out.println( "new value = " + constantTs.getImage() );

                    variableContantMap.put(ts.getImage(), constantTs.getImage());        //[old variable, new constants]
                    //Replace param
                    new_paramList.add(constantTs);
                }
            }

            //----------------------------------------------------------------
            //Manage effect. Use variableContantMap to replace value in effect.
            //----------------------------------------------------------------

            //(ArrayList<TypedSymbol>) actionList.get(0).getParameters();
            //ArrayList<TypedSymbol> paramList = (ArrayList<TypedSymbol>) actionList.get(0).getParameters();
            Exp effects = (Exp) allActionList.getEffects();

            //transformEffectToGround(effects, variableContantMap);

            //Add new paramList to groundActionList
            //TODO: Use the new object, instead of the old one.
            Op old_action = allActionList;
            Op new_action = new Op(old_action);
            new_action.setParameters(new_paramList);        //set params
            new_action.setEffects(effects);            //TODO: Clone before assign
            OpUtil.groundActionList.add(new_action);
            //        System.out.println("groundAction");
            //        System.out.println(OpUtil.groundActionList.toString());
        }
    }

    public static boolean transformStudentActionToGround(DataStream dt)
    {
        boolean isStudentInputEmpty = false;

        if (dt == null || dt.getStudentActionList() == null || dt.getStudentActionList().isEmpty()){
            isStudentInputEmpty = true;
        }
        else
        {
            StudentActionUtil.studentActionList.clear();
            StudentActionUtil.studentActionList.add(dt.getStudentActionList());
        }
        return isStudentInputEmpty;
    }

    public static void processInference( List<Exp> currentFacts )
    {
        processFactAndDA( currentFacts );
    }

    public static void processFactAndDA( List<Exp> currentFacts )
    {
        //System.out.println("-- processFactAndDA --");

        List<Exp> remove_fact = new ArrayList<>();

        Exp implies = null;

        //1. for each context in each DA
        for( DomainAxiom da : groundDAList )
        {
            Exp context = da.getContext();
            String contextString = getAtomStringFromPredicate(context);

            if( context.getAtom() != null
                    || ( context.getConnective().equals(Connective.NOT)
                    && ( context.getChildren().get(0).getAtom() != null ) ) )
            {
                if( Utils.isAvailable( currentFacts, da.getContext() ) )
                {
                    implies = getImpliesFromDA( currentFacts, da );


                    if( implies != null )
                    {
                        //update current fact.
                        //TODO: Refactor to function
                        //NOTE: At this moment, support only singleton predicates. (to apply to the complex predicates, it is needed to improved this)
                        //Done//TODO: Make sure if you need to delete the negative facts too. because the purpose is to create graph
//                        System.err.println("Implies: "+implies);

                        //Firstly, check predicate is already exist in currentFact or not
                        boolean exist = isPredicateAlreadyExist(currentFacts, implies);
                        if(exist){
                            //If this predicate exists then check is it negated or not
                            Exp remove_exp = getPredicateThatNegated(currentFacts, implies);
                            if(remove_exp != null) {//if remove_exp != null it means this predicate is negated
                                currentFacts.remove(remove_exp);//If it is negated then remove the old predicate
                                currentFacts.add(implies);//and add the new predicate
                            }
                            else
                            {
                                //else remove_exp == null it means this predicate is the same then - not add anything
                            }
                        }
                        else if(!exist){//If this predicate is not exist then add
                            currentFacts.add( implies );
                        }


                        //TODO: recursive call here. by using the latest update factlist and pass with domain rules.
                        //processFactAndDA(currentFacts)
                    }
                    else
                    {
                        //exit
                    }
                }
                else
                {
                    for( Exp cExpChild : currentFacts ) {

                        String factString = getAtomStringFromPredicate(cExpChild);
                        if(factString.equals(contextString))
                        {
                            //If this fact has NOT Connective
                            if(cExpChild.getConnective().equals(Connective.NOT))
                            {
                                //in case (ex. when (NOT (COLOR RED)) -> (COLOR RED) then remove Comprehension)
                                if(cExpChild.getChildren().get(0).getAtom().size() == 2)
                                {
//                                    String factSecondAtom = InferenceEngineUtil.getSecondAtomStringFromPredicate(cExpChild);
//                                    String daSecondAtom = InferenceEngineUtil.getSecondAtomStringFromPredicate(da.getContext());
//
//                                    if(!factSecondAtom.equals(daSecondAtom)) remove_fact.add(da.getImplies());

                                    remove_fact.add(da.getImplies());
                                }
                                //then if their connective are conflict
                                else if(!cExpChild.getConnective().equals(da.getContext().getConnective()))
                                {
                                    //ex. ~P -> C then remove C
                                    //remove Comprehension out from fact list
                                    remove_fact.add(da.getImplies());
                                }
                            }
                            else if (cExpChild.getAtom() != null){

                                //in case like (COLOR BLUE) and (COLOR RED)
                                if(cExpChild.getAtom().size() == 2)
                                {
                                    String factSecondAtom = getSecondAtomStringFromPredicate(cExpChild);
                                    String daSecondAtom = getSecondAtomStringFromPredicate(da.getContext());

                                    if(!factSecondAtom.equals(daSecondAtom)) remove_fact.add(da.getImplies());
                                }
                            }
                        }
                    }
                }
            }
            else
            {
                //TODO: Support.
                //In case of A and B and C --> D
            }
        }

        //in case that there are fact to remove
        if(remove_fact.size() > 0)
        {
            for(Exp eRemove_fact : remove_fact)
            {
                WorldStateUtil.getCurrentFactAndEffect().remove(eRemove_fact);
            }
        }
    }

    public static String getAtomStringFromPredicate(Exp predicate)
    {
        String atomString = "";

        if(predicate.getAtom() != null)
        {
            return predicate.getAtom().get(0).toString();
        }
        else
        {
            if(predicate.getChildren().size() != 0)
            {
                atomString = getAtomStringFromPredicate(predicate.getChildren().get(0));
            }
        }

        return atomString;
    }

    public static String getSecondAtomStringFromPredicate(Exp predicate)
    {
        String atomString = "";

        if(predicate.getAtom() != null)
        {
            if(predicate.getAtom().size() == 2) return predicate.getAtom().get(1).toString();
        }
        else
        {
            if(predicate.getChildren().size() != 0)
            {
                atomString = getSecondAtomStringFromPredicate(predicate.getChildren().get(0));
            }
        }

        return atomString;
    }

    private static boolean isPredicateAlreadyExist(List<Exp> currentFacts , Exp implies)
    {
        boolean exist = false;

        for(Exp currentFactsList : currentFacts)
        {
            Exp expImplies = PlanProjectionUtil.getChildrenNodeFromExp(implies);
            Exp expCurrentFact = PlanProjectionUtil.getChildrenNodeFromExp(currentFactsList);
            if(expImplies.equals(expCurrentFact)) {
                exist = true;
//                System.err.println("expCurrentFactsList "+ expCurrentFact
//                        +" implies "+ expImplies +" already exist!");
            }
        }

        return exist;
    }

    private static Exp getPredicateThatNegated(List<Exp> currentFacts , Exp implies)
    {
        Exp remove_exp = null;

        for(Exp currentFactsList : currentFacts)
        {
            Exp expImplies = PlanProjectionUtil.getChildrenNodeFromExp(implies);
            Exp expCurrentFact = PlanProjectionUtil.getChildrenNodeFromExp(currentFactsList);
            if(expImplies.equals(expCurrentFact) && !implies.getConnective().equals(currentFactsList.getConnective()))
            {
//                System.err.println("expCurrentFactsList "+ currentFactsList + currentFactsList.getConnective()
//                        +" implies "+ implies + implies.getConnective() + " are negated!");
                remove_exp = currentFactsList;
            }
        }

        return remove_exp;
    }

    private static Exp getImpliesFromDA( List<Exp> currentFacts,  DomainAxiom da )
    {
        Exp impliesExp = null;

        for( Exp ex_item: currentFacts)
        {
            //System.out.println("expList(item) : " + ex_item.toString() );

            if( da.getContext().equals( ex_item ) )
            {
                impliesExp = da.getImplies();
            }
        }

        return impliesExp;
    }

    private static void addToTypeMap ( TypedSymbol value )
    {
        //TypedSymbol variable = value;
        String variable_type = value.getTypes().get(0).getImage();

        //check if type exist.
        if( typeMap.containsKey( variable_type ) )
        {
            ArrayList<TypedSymbol> varList = typeMap.get(variable_type);
            varList.add( value );
            typeMap.put( variable_type, varList );

        }
        else
        {
            ArrayList<TypedSymbol> varList = new ArrayList<>();
            varList.add( value );
            typeMap.put(variable_type, varList);
        }

    }

    public static void generateTypeMap(Parser parser )
    {
        ArrayList<TypedSymbol> objectList = (ArrayList) parser.getProblem().getObjects();

        //---------------------------------------
        //1. Check object list to create typedMap
        //---------------------------------------
        for( TypedSymbol t : objectList )
        {
            addToTypeMap( t );
        }

        //---------------------------------------
        // 2. check constants used in initial situation
        //---------------------------------------
        //2.1  check if there is constants in Problem.init, keep Symbol (image = PREMOLAR, kind = CONSTANTS)
        //2.2 if found, get the type of constant from domain.constant, keep TypedSymbol ( image = PREMOLAR, kind = CONSTANTS, types.get(0) [Symbol] = image = toothTypeConstants, kind = type.
        //2.3 add to type map  (string, ArrayList<TypedSymbol>)

        Symbol constant_symbol = getConstantSymbolFromProblem( parser.getProblem() );
        if( constant_symbol != null )
        {
            //get type of constant from domain
            TypedSymbol typed_symbol  = getConstantTypedSymbolFromDomain( parser.getDomain(), constant_symbol );

            //Add to type map.
            addToTypeMap( typed_symbol );
        }

//        System.out.println( "--> TypeMap (ToString()): " +  typeMap.toString() );
    }

    private static TypedSymbol getConstantTypedSymbolFromDomain(Domain domain, Symbol c_symbol )
    {
        if( domain == null || domain.getConstants() == null )
            return null;

        TypedSymbol typed_symbol = domain.getConstant( c_symbol );

        return typed_symbol;
    }

    private static Symbol getConstantSymbolFromProblem( Problem problem )
    {
        if( problem == null || problem.getInit() == null )
            return null;

        boolean isFound = false;
        Symbol constant_symbol = null;

        for( Exp e : problem.getInit() )
        {
            if( e.getAtom() != null )
            {
                for( Symbol s : e.getAtom() )
                {
                    //if( s.getKind().name().equals( Symbol.Kind.CONSTANT ) )
                    if( s.getKind().equals( Symbol.Kind.CONSTANT ) )
                    {
                        isFound = true;
                        constant_symbol = s;
                    }
                }

            }

        }

        return constant_symbol;
    }

    public static Exp getStudentPerceptionFromAxiom(Exp perceptionExp)
    {
        Exp return_perception = null;

        for( DomainAxiom da : groundDAList ) {

            Exp context = da.getContext();
            Exp implies = da.getImplies();

            if(context.equals(perceptionExp))
            {
                return_perception = context;
//                System.out.println("toey return_perception: " +return_perception);
            }
            else if(implies.equals(perceptionExp)) return_perception = context;

        }

        return return_perception;
    }

    public static Exp getANDPerceptionFromAxiom(List<Exp> predicateList)
    {
        Exp return_perception = null;

        for( DomainAxiom da : groundDAList ) {

            Exp context = da.getContext();
            Exp implies = da.getImplies();

            Exp context_clone = null;

            int impliesCountFound = 0;
            int contextCountFound = 0;

            List<Exp> predicateFromContext = new ArrayList<>();
            List<Exp> predicateFromImplies = new ArrayList<>();

            if(context.getConnective().equals(Connective.AND))
            {
                for(Exp each_context : context.getChildren())
                {
                    predicateFromContext.add(each_context);
                }
                context_clone = context;
            }
            if(implies.getConnective().equals(Connective.AND))
            {
                for(Exp each_implies : implies.getChildren())
                {
                    predicateFromImplies.add(each_implies);
                }
            }

            if(predicateFromContext.size() != 0)
            {
                for(Exp each_context : predicateFromContext)
                {
                    String contextString = Utils.getExpName(each_context);
                    boolean found = false;
                    for(Exp each_predicate : predicateList)
                    {
                        String predicateString = Utils.getExpName(each_predicate);
                        System.out.println("toey "+contextString + " - " + predicateString);
                        if(contextString.equals(predicateString))
                        {
//                            return_perception.add(each_context);
                            contextCountFound = contextCountFound + 1;
                            found = true;
                            break;
                        }
                    }

                    if(!found) break;
                }
            }
            if(predicateFromImplies.size() != 0)
            {
                for(Exp each_implies : predicateFromImplies)
                {
                    String impliesString = Utils.getExpName(each_implies);
                    boolean found = false;
                    for(Exp each_predicate : predicateList)
                    {
                        String predicateString = Utils.getExpName(each_predicate);
                        System.out.println("toey2 "+impliesString + " - " + predicateString);
                        if(impliesString.equals(predicateString))
                        {
//                            return_perception.add(each_implies);
                            impliesCountFound = impliesCountFound + 1;
                            found = true;
                            break;
                        }
                    }

                    if(!found) break;
                }
            }

            if(impliesCountFound == predicateFromImplies.size() && context_clone != null)
            {
                return_perception = context_clone;
            }
            else if(contextCountFound == predicateFromContext.size() && context_clone != null)
            {
                return_perception = context_clone;
            }
        }

        return return_perception;
    }

    public static List<Exp> getTutorPerceptionListFromAxiom(Exp predicateExp)
    {
        List<Exp> return_perception = new ArrayList<>();

        String predicateString = Utils.getPredicateString(predicateExp);

        for( DomainAxiom da : groundDAList ) {

            Exp context = da.getContext();
            Exp implies = da.getImplies();

            String contextString = Utils.getPredicateStringFromExp(context);
            String impliesString = Utils.getPredicateStringFromExp(implies);

            if(contextString.equals(predicateString))
            {
                return_perception.add(context);
            }
            else if(impliesString.equals(predicateString))
            {
                return_perception.add(context);
            }

//            if(context.equals(predicateExp))
//            {
//                return_perception = context;
////                System.out.println("toey return_perception: " +return_perception);
//            }
//            else if(implies.equals(predicateExp))
//            {
//                return_perception = context;
//            }

        }

        return return_perception;
    }

    public static Exp getComprehensionFromAxiom(Exp perceptionExp)
    {
        Exp return_comprehension = null;

        for( DomainAxiom da : groundDAList ) {

            Exp context = da.getContext();

            if(context.equals(perceptionExp))
            {
                return_comprehension = da.getImplies();
//                System.out.println("toey return_comprehension: " + return_comprehension);
            }
        }

        return return_comprehension;
    }

    public static Exp getComprehensionANDFromAxiom(Exp perceptionExp)
    {
        Exp return_comprehension = null;

        for( DomainAxiom da : groundDAList ) {

            Exp context = da.getContext();

            if(context.getConnective().equals(Connective.AND))
            {
                if(context.equals(perceptionExp))
                {
                    return_comprehension = da.getImplies();
                }
            }
        }

        return return_comprehension;
    }

    //-----------------------
    // Nui
    //-----------------------
    public static boolean isProjectionExp(Exp inExp, List<Exp> tutorPjExpList )
    {
        boolean isProjection = false;
        List<Exp> tutorFlatPjExpList = InterventionStringUtil.flattenExpList( tutorPjExpList, null );

        if( tutorFlatPjExpList.contains( inExp ) )
        {
            isProjection = true;
        }

        return isProjection;
    }

    public static boolean isCompoundPredicate(Exp exp)
    {
        boolean isCompoundPredicate = false;
        if( exp != null )
        {
            if( exp.getConnective().equals( Connective.AND ))
            {
                isCompoundPredicate = true;
                return true;
            }

        }
        return false;
    }

    public static boolean isComprehensionExp(Exp exp)
    {
        boolean isComprehension = false;

//        if( exp.getConnective().equals( Connective.AND ) )
//        {
//            //TODO: You need to extract each exp and find there is comprehension node and return it back
//            //Skipp this first.
//            return false;
//        }

        for( DomainAxiom da : groundDAList )
        {
            Exp context = da.getContext();
            Exp implies = da.getImplies();

            if(context.equals( exp ))
            {
                //Do nothing
            }
            else if(implies.equals( exp ))
            {
                isComprehension = true;
                break;
            }
        }

        return isComprehension;
    }

    //Exp pExp = InferenceEngineUtil.getPerceptionFromComprehensionExp( exp );
    public static Exp getPerceptionFromComprehensionExp( Exp cExp )
    {
        Exp pExp = null;

//        if( cExp.getConnective().equals( Connective.AND ) )
//        {
//            //TODO: You need to extract each exp and find there is comprehension node and return it back
//            //Skipp this first.
//            return null;
//        }


        for( DomainAxiom da : groundDAList )
        {
            Exp context = da.getContext();
            Exp implies = da.getImplies();

            if(context.equals( cExp ))
            {
                //Do nothing
            }
            else if(implies.equals( cExp ))
            {
                pExp = context;
                break;
            }
        }
        return pExp;
    }

    public static boolean isPerceptionPredicateExp( Exp exp )
    {
        boolean isPerception = false;

        boolean isComprehension = isComprehensionExp( exp );
        boolean isUserAction = isUserPredicateExp( exp );

        //if answer is projection, get parent (action condition) node. pick by priority
        if( isComprehension )
        {
            isPerception = false;
        }
        else if( isUserAction )
        {
            isPerception = false;
        }
        else
        {
            isPerception = true;
        }

        return isPerception;
    }

    public static boolean isUserPredicateExp( Exp exp )
    {
        boolean isUserPredicate = false;
        if( exp == null )
        {
            return false;
        }

//        if( exp.getConnective().equals( Connective.AND ) )
//        {
//            //TODO: You need to extract each exp and find there is comprehension node and return it back
//            //Skipp this first.
//            return false;
//        }

        //1. Get the first symbol from exp
//        Symbol symbol = get1stPredicateFromExp( exp );
        String predicate = InterventionStringUtil.get1stPredicateStringFromExp( exp );
        if( predicate == null )
        {
            return false;
        }

        //2. Evaluate if it is user predicate
        String value = ApplicationController.action_predicate_map_reverse.get( predicate );

        if( value != null && value.trim().length() > 0 )
        {
            isUserPredicate = true;
        }

        return isUserPredicate;
    }
}
