package domRabFSM.BuyerBeh;

import domRabFSM.*;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;

import java.util.List;

public class FSMbehBuyer extends FSMBehaviour {
    private final static String START = "start";
    private final static String WAIT = "wait";
    private final static String SUCCESS = "success";
    private final static String FAIL = "fail";
    private final static String END = "END";
    private String currency;
    private List<AID> sellers;


    /**
     *     FSMBeh - поведение, которое должно приводить нас к какому-то конечному состоянию; суть в прописании логики,
     *     когда мы прописываем какое состояние мы получим в выходе другого.
     *     FirstState - первое состояние
     *     State - промежуточные состояния
     *     LastState - полученное в результате однного из State'ов; LastState'ов мб несколько
     */
    public FSMbehBuyer(Agent agent, String currency, List<AID> sellers){
        registerFirstState(new SendRequest(agent,currency,sellers), START);
        registerState(new WaitingForResponse(agent, currency, sellers, 1000), WAIT);
//        registerState(new Parallel8(agent,new WaitingForResponse(agent,1000),
//                new Killer(agent,5000)),WAIT);
        registerState(new Success(agent),SUCCESS);
        registerState(new Fail(agent),FAIL);
        registerLastState(new ENd(agent),END);

        registerDefaultTransition(START,WAIT);
        registerTransition(WAIT, SUCCESS, 1);
        registerTransition(WAIT, FAIL, 2);
        registerTransition(WAIT, SUCCESS, 0);
        registerDefaultTransition(SUCCESS,END);
        registerDefaultTransition(FAIL,END);

    }

}
