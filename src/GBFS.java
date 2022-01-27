import java.util.PriorityQueue;
import java.util.*;

class GBFSComparator implements Comparator<GState> {
    @Override
    public int compare(GState g1, GState g2) {
        return g1.getHeuristic() > g2.getHeuristic() ? 1 : -1;
    }
}

class GState {
    private State state;
    private int heuristic;

    public GState(State state, int heuristic) {
        this.state = state;
        this.heuristic = heuristic;
    }

    public State getState() {
        return state;
    }

    public int getHeuristic() {
        return heuristic;
    }

}

public class GBFS {
    
    public static void search(State state) {
        PriorityQueue<GState> frontier = new PriorityQueue<GState>(new GBFSComparator());
        GState initialState = new GState(state, H.getH(state));
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();
        if (Check.isGoal(initialState.getState())) {
            Check.result(initialState.getState(), "GbfsResult");;
            return;
        }
        frontier.add(initialState);
        inFrontier.put(initialState.getState().hash(),true);
        while (!frontier.isEmpty()){

            GState tempState = frontier.poll();

            if (Check.isGoal(tempState.getState())) {
                Check.result(tempState.getState(), "GbfsResult");;
                return;
            }


            inFrontier.remove(initialState.getState().hash());
            explored.put(initialState.getState().hash(),true);
            ArrayList<State> children = tempState.getState().successor();
            for(int i = 0;i<children.size();i++){
                if(!(inFrontier.containsKey(children.get(i).hash()))
                        && !(explored.containsKey(children.get(i).hash()))) {
                    
                    frontier.add(new GState(children.get(i), H.getH(children.get(i))));
                    inFrontier.put(children.get(i).hash(), true);
                }
            }
        }
    }
}
