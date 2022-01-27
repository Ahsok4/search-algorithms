import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

public class DFS {
    public static void search(State initialState) {
        
        Stack<State> frontier = new Stack<State>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();

        if(Check.isGoal(initialState)){
            Check.result(initialState, "DfsResult");
            return;
        }

        frontier.push(initialState);
        inFrontier.put(initialState.hash(),true);

        while(!frontier.isEmpty()) {
            State tempState = frontier.pop();
            inFrontier.remove(tempState.hash());
            explored.put(tempState.hash(),true);
            ArrayList<State> children = tempState.successor();

            if(Check.isGoal(tempState)){
                Check.result(tempState, "DfsResult");
                return;
            }

            for(int i = 0;i<children.size();i++){
                if(!(inFrontier.containsKey(children.get(i).hash()))
                        && !(explored.containsKey(children.get(i).hash()))) {
                    frontier.push(children.get(i));
                    inFrontier.put(children.get(i).hash(), true);
                }
            }
        }
    }
}
