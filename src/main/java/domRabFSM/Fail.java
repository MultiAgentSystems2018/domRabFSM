package domRabFSM;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class Fail extends OneShotBehaviour {
    private Agent agent;

    public Fail(Agent agent){
        this.agent = agent;
    }

    @Override
    public void action() {
        System.out.println(agent.getLocalName() + " ENDED WITH FAIL!");
    }
}
