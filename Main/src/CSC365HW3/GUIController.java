package CSC365HW3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by landon on 5/11/17.
 */
public class GUIController {

    private ArrayList<String> pages;
    private HashSet<String> pageChecker;
    private Graphing dj;
    private ObservableList<String> pageList;


    @FXML
    private Label cacheCheck;

    @FXML
    private TextField urlInput;

    @FXML
    private ComboBox<String> sources;

    @FXML
    private ComboBox<String> destinations;

    @FXML
    private Label pathLength;

    @FXML
    private Label mspS;

    @FXML
    private Label mspD;

    @FXML
    private Label MSTspanningTree;

    @FXML
    private ListView<String> path;

    /**
     * Initialization method
     */

    public void initialize(){
        this.pages = new ArrayList<>();
        this.dj = new Graphing();
        this.pageChecker = new HashSet<>();
    }

    /**
     * Helper method
     * @return whether or not a cache of pages exists
     * @throws Exception
     */

    boolean cacheCheck() throws Exception {
        if(!dj.checkSerializedExists()) {
            cacheCheck.setText("Cache does not exist");
            return false;
        } else {
            cacheCheck.setText("Cache exists!");
            pages = dj.getList();
            return true;
        }
    }

    /**
     * Handles when the user wants to check the cache and sets the lists of pages if the cache exists
     * @throws Exception
     */

    @FXML
    void handleCacheCheck() throws Exception {
        if(cacheCheck()) {
            setter();
        }
    }

    /**
     * Method to update the GUI with the path length, most similar pages, and MST weight
     */

    @FXML
    void handlePathSubmit(){
        dj.checkSerializedExists();
        String src = sources.getSelectionModel().getSelectedItem();
        String dest = destinations.getSelectionModel().getSelectedItem();

        ArrayList<String> path = dj.dijkstra(src, dest);
        String pathValue = path.get(path.size() - 1);

        pathLength.setText(String.valueOf(pathValue.substring(pathValue.lastIndexOf(" ") + 1)));
        String[] similarities = dj.getMostSimilar(src, dest);
        mspS.setText(similarities[0]);
        mspD.setText(similarities[1]);

        MSTspanningTree.setText(String.valueOf(dj.kruskal()));

        ObservableList<String> items = FXCollections.observableArrayList (path);
        this.path.setItems(items);

    }

    /**
     * Method to grab URL specified with all children links
     * @throws Exception
     */

    @FXML
    void onEnter() throws Exception {
        pages.clear();
        MostCommonWords.INSTANCE.readCommonWords();
        DataPuller d = new DataPuller(urlInput.getText());
        ArrayList<WikiPage> w = d.grabData();
        w.forEach(p -> {
            if(!pageChecker.contains(p.getTitle())) {
                pageChecker.add(p.getTitle());
                pages.add(p.getTitle());
            }
        });

        dj.addAll(w);

        dj.serializePages();

        setter();
    }

    /**
     * Helper method to set the lists of source and destination pages
     */

    void setter(){
        pageList = FXCollections.observableList(pages);

        sources.setItems(pageList);
        sources.getSelectionModel().selectFirst();
        destinations.setItems(pageList);
        destinations.getSelectionModel().selectFirst();
    }


}
