import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

 public class  BDS {

    public static void search(State initialState, State goal){
        Queue<State> frontier = new LinkedList<State>();
        Queue<State> back = new LinkedList<State>();

        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> inBack = new Hashtable<>();

        Hashtable<String, Boolean> explored_front = new Hashtable<>();
        Hashtable<String, Boolean> explored_back = new Hashtable<>();

        if(Check.isGoal(initialState)){
            Stack<State> stack = new Stack<>();
            stack.push(initialState);
            result(stack);
            return;
        }

        frontier.add(initialState);
        back.add(goal);
        inFrontier.put(initialState.hash(),true);
        inBack.put(goal.hash(), true);

        while (!frontier.isEmpty() && !back.isEmpty()){
            State tempState = frontier.poll();
            State tempBack = back.poll();
            inFrontier.remove(tempState.hash());
            inBack.remove(tempBack.hash());
            explored_front.put(tempState.hash(),true);
            explored_back.put(tempBack.hash(), true);

            ArrayList<State> children_front = tempState.successor();
            ArrayList<State> children_back = tempBack.successor();
            
            for(int i = 0;i<children_front.size();i++){
                if(!(inFrontier.containsKey(children_front.get(i).hash()))
                        && !(explored_front.containsKey(children_front.get(i).hash()))) {
                    
                    frontier.add(children_front.get(i));
                    inFrontier.put(children_front.get(i).hash(), true);
                }
            }

            for(int i = 0;i<children_back.size();i++){
                if(!(inBack.containsKey(children_back.get(i).hash()))
                        && !(explored_back.containsKey(children_back.get(i).hash()))) {
                   
                    back.add(children_back.get(i));
                    inBack.put(children_back.get(i).hash(), true);
                }
            }

            Stack<State> tempStack = new Stack<>();
            Stack<State> stack = new Stack<>();

            State frontFoundState = null;
            State backFoundState = null;

            for (State s : frontier) {
                for (State state : back) {
                    if (s.hash().equals(state.hash())) {
                        frontFoundState = s;
                        backFoundState = state;

                        while (backFoundState != null) {
                            tempStack.push(backFoundState);
                            // System.out.println(backFoundState.outputGenerator() + " baaaack \n");
                            backFoundState = backFoundState.getParentState();
                        }
            
                        while (!tempStack.isEmpty()) {
                            stack.push(tempStack.pop());
                        }
                        frontFoundState = frontFoundState.getParentState();
                        while (frontFoundState != null) {
                            stack.push(frontFoundState);
                            // System.out.println(frontFoundState.outputGenerator() + "frooooont \n");

                            frontFoundState = frontFoundState.getParentState();
                        }
            
                        result(stack);
                        return;
                    }
                }
            }
        }
    }

    private static void result(Stack<State> states){
        
        try {
            FileWriter myWriter = new FileWriter("BdsResult.txt");
            // System.out.println("initial state : ");
            while (!states.empty()){
                State tempState = states.pop();
                if(tempState.getSelectedNodeId() != -1) {
                    // System.out.println("selected id : " + tempState.getSelectedNodeId());
                }
                // tempState.getGraph().print();

                myWriter.write(tempState.getSelectedNodeId()+" ,");
                myWriter.write(tempState.outputGenerator()+"\n");
            }
            myWriter.close();
            System.out.println("BdsResult Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}


