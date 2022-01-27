import java.util.*;

class UComparator implements Comparator<UState> {
    
    @Override
    public int compare(UState u1, UState u2) {
        return u1.getG() - u2.getG();
    }
}

class UState {
    private State state;
    private int g;

    public UState(State state, int g) {
        this.state = state;
        this.g = g;
    }

    public State getState() {
        return this.state;
    }

    public int getG() {
        return this.g;
    }
}


public class  UCS {

    public static void search(State initialState){
        PriorityQueue <UState> frontier = new PriorityQueue<>(new UComparator());

        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();
        if(Check.isGoal(initialState)){
            Check.result(initialState, "UcsResult");
            return;
        }

        frontier.add(new UState(initialState, 0));
        inFrontier.put(initialState.hash(),true);
        while (!frontier.isEmpty()){
            UState tempState = frontier.poll();
            if (Check.isGoal(tempState.getState())) {
                Check.result(tempState.getState(), "UcsResult");
                return;
            }
            ArrayList<State> children = tempState.getState().successor();
            for(int i = 0;i<children.size();i++){
               
                if(!(inFrontier.containsKey(children.get(i).hash()))
                        && !(explored.containsKey(children.get(i).hash()))) {
                            int g = 0;
                            Color c = tempState.getState().getGraph().getNode(children.get(i).getSelectedNodeId()).getColor();
                            if (c.equals(Color.Red)) {
                                g = 1;
                            } else if (c.equals(Color.Green)) {
                                g = 3;
                            } else if (c.equals(Color.Black)) {
                                g = 2;
                            }
                                
                    frontier.add(new UState(children.get(i), g + tempState.getG()));
                    inFrontier.put(children.get(i).hash(), true);
                    explored.put(children.get(i).hash(), true);
                }
            }
        }
    }
}


