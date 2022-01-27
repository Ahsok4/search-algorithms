import java.util.*;

class AStarComparator implements Comparator<AState> {
    @Override
    public int compare(AState a1, AState a2) {
        return a1.getG() + a1.getH() > a2.getG() + a2.getH() ? 1 : -1;
    }
}

class AState {
    private State state;
    private int g;
    private int h;

    public AState(State state, int g, int h) {
        this.state = state;
        this.h = h;
        this.g = g;
    }

    public State getState() {
        return this.state;
    }

    public int getH() {
        return this.h;
    }

    public int getG() {
        return this.g;
    }
}

public class AStar {
    public static void search(State state) {

        PriorityQueue<AState> frontier = new PriorityQueue<AState>(new AStarComparator());
        AState initialState = new AState(state, 0, H.getH(state));
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();
        if(Check.isGoal(initialState.getState())){
            Check.result(initialState.getState(), "AstarResult");
            return;
        }
        frontier.add(initialState);
        inFrontier.put(initialState.getState().hash(),true);
        while (!frontier.isEmpty()){
            AState tempState = frontier.poll();    
            if(Check.isGoal(tempState.getState())){
                Check.result(tempState.getState(), "AstarResult");
                return;
            }            
            inFrontier.remove(initialState.getState().hash());
            explored.put(initialState.getState().hash(),true);
            ArrayList<State> children = tempState.getState().successor();
            for(int i = 0;i<children.size();i++){
                if(!(inFrontier.containsKey(children.get(i).hash()))
                        && !(explored.containsKey(children.get(i).hash()))) {
                    frontier.add(new AState(children.get(i), tempState.getG() + 1, H.getH(children.get(i))));
                    inFrontier.put(children.get(i).hash(), true);
                }
            }
        }

    }
}
