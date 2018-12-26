package domRabFSM;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class Success extends OneShotBehaviour {
    private Agent agent;

    public Success(Agent agent){
        this.agent = agent;
    }
    @Override
    public void action() {
        System.out.println(agent.getLocalName() + " ENDED WITH SUCCESS!");
    }
}
