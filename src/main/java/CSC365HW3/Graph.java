package CSC365HW3;

import java.util.ArrayList;

/**
 * Created by landon on 4/29/17.
 */
class Graph {

    class Edge implements Comparable<Edge>{
        WikiPage s, d;
        double weight;

        Edge(WikiPage s, WikiPage d, double w){
            this.s = s;
            this.d = d;
            this.weight = w;
        }

        public WikiPage getS() {
            return s;
        }

        public WikiPage getD() {
            return d;
        }

        public double getWeight() {
            return weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Double.compare(this.weight, o.weight);
        }


    }


    Graph(){

    }

}
