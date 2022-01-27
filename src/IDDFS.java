import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

public class IDDFS {

    public static void search(State initialState, int maxDepth) {

        for(int j = 0; j <= maxDepth; j++) {
            Stack<State> frontier = new Stack<State>();
            // System.out.println("j is \n" + j);
            Hashtable<String, Boolean> inFrontier = new Hashtable<>();
            Hashtable<String, Boolean> explored = new Hashtable<>();

            if(Check.isGoal(initialState)){
                Check.result(initialState, "IddfsResult");
                return;
            }

            frontier.push(initialState);
            inFrontier.put(initialState.hash(),true);
            
            while(!frontier.isEmpty()) {
                State tempState = frontier.pop();
                inFrontier.remove(tempState.hash());
                explored.put(tempState.hash(),true);
                // System.out.println("depth : " + tempState.getDepth());
                if (Check.isGoal(tempState)) {
                    Check.result(tempState, "IddfsResult");
                    return;
                }
                if (tempState.getDepth() <= j) {
                    ArrayList<State> children = tempState.successor();
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
        
    }
}
