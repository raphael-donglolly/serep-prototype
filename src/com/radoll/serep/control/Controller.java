package com.radoll.serep.control;

import com.radoll.serep.AlertHelper;
import com.radoll.serep.factory.TemplateConverter;
import com.radoll.serep.models.DockModel;
import com.radoll.serep.models.LayoutPosition;
import com.radoll.serep.models.RemovableNodes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button addTrackButton;

    private Window window;

    private static final String TRACK = "track";
    public static final String DOCK = "dock";
    private static final String CODE = "code";

    private static final double OFFSET_FIELDS = 80.0;

    public void handleAddTrackButtonAction(ActionEvent actionEvent) {

        final Label track = new Label();
        track.setId("track_0" + getNextTrackId());
        track.setText(String.valueOf(getNextTrackId()));
        track.setLayoutX(getLabelDock(TRACK).getX());
        track.setLayoutY(getLabelDock(TRACK).getY());

        final TextField dock = new TextField();
        dock.setId("dock_0" + getNextTrackId());
        dock.setLayoutX(getTextFieldDock(DOCK).getX());
        dock.setLayoutY(getTextFieldDock(DOCK).getY());
        dock.setMaxWidth(50.0);

        final Label code = new Label();
        code.setId("code_0" + getNextTrackId());
        code.setLayoutX(getLabelDock(CODE).getX());
        code.setLayoutY(getLabelDock(CODE).getY());
        code.setStyle("-fx-border-color: black; -fx-padding: 2");
        code.setMinWidth(15.0);
        code.setText("x" + getNextTrackId());

        anchorPane.getChildren().addAll(track, dock, code);
    }


    public void handleRemoveTrackButtonAction(ActionEvent actionEvent) {

        window = addTrackButton.getScene().getWindow();


        if (getLastTrack().getTrack().getId().contains("_01")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error!",
                    "First track cannot be deleted.");
            return;

        }

        anchorPane.getChildren().remove(getLastTrack().getTrack());
        anchorPane.getChildren().remove(getLastTrack().getDock());
        anchorPane.getChildren().remove(getLastTrack().getCode());


    }

    public void handleSubmitButtonAction(ActionEvent actionEvent) {

        checkTextfields(TRACK);
        checkTextfields(DOCK);


        final List<Node> collect = anchorPane.getChildren().stream().filter(node -> node instanceof TextField).collect(Collectors.toList());


        String rs = "";

        for (Node node : collect) {

            if (node.getId().contains(TRACK)) {
                rs = rs + ((TextField) node).getText() + "\t - \t";
            }

            if (node.getId().contains(DOCK)) {
                rs = rs + ((TextField) node).getText() + "\n";
            }
        }

        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Werteübermittlung", rs);

    }

    private RemovableNodes getLastTrack() {
        final RemovableNodes removableNodes = new RemovableNodes();


        removableNodes.setTrack(getLabelLastTrack(TRACK));
        removableNodes.setDock(getLastTrack(DOCK));
        removableNodes.setCode(getLastTrackLabel(CODE));

        return removableNodes;
    }


    private Node getLabelLastTrack(String id) {
        return anchorPane
                .getChildren()
                .stream()
                .filter(innerNode -> innerNode instanceof Label)
                .filter(innerNode -> innerNode.getId().contains(id))
                .reduce((first, second) -> second).get();
    }


    private Node getLastTrack(String id) {
        return anchorPane
                .getChildren()
                .stream()
                .filter(innerNode -> innerNode instanceof TextField)
                .filter(innerNode -> innerNode.getId().contains(id))
                .reduce((first, second) -> second).get();
    }

    private Node getLastTrackLabel(String id) {
        return anchorPane
                .getChildren()
                .stream()
                .filter(innerNode -> innerNode instanceof Label)
                .filter(innerNode -> innerNode.getId().contains(id))
                .reduce((first, second) -> second).get();
    }


    private int getNextTrackId() {

        final Node node = anchorPane
                .getChildren()
                .stream()
                .filter(innerNode -> innerNode instanceof Label)
                .filter(innerNode -> innerNode.getId().contains(TRACK))
                .reduce((first, second) -> second).get();

        final String[] split = node.getId().split("_");

        return Integer.valueOf(split[1]) + 1;
    }


    private LayoutPosition getLabelDock(final String id) {


        final List<Node> collect = anchorPane
                .getChildren()
                .stream()
                .filter(innerNode -> innerNode instanceof Label)
                .filter(innerNode -> innerNode.getId().contains(id)).collect(Collectors.toList());


        final Node node = collect.get(collect.size() - 1);

        final double xadd = node.getLayoutX() + OFFSET_FIELDS;

        final LayoutPosition layoutPosition = new LayoutPosition();
        layoutPosition.setX(xadd);
        layoutPosition.setY(node.getLayoutY());

        return layoutPosition;

    }


    private LayoutPosition getTextFieldDock(final String id) {


        final List<Node> collect = anchorPane
                .getChildren()
                .stream()
                .filter(innerNode -> innerNode instanceof TextField)
                .filter(innerNode -> innerNode.getId().contains(id)).collect(Collectors.toList());


        final Node node = collect.get(collect.size() - 1);

        final double xadd = node.getLayoutX() + OFFSET_FIELDS;


        final LayoutPosition layoutPosition = new LayoutPosition();
        layoutPosition.setX(xadd);
        layoutPosition.setY(node.getLayoutY());

        return layoutPosition;
    }


    private void checkTextfields(final String id) {


        int lanes = 0;

        for (Node node : anchorPane.getChildren()) {
            if (node instanceof TextField) {
                if (node.getId().contains(id)) {
                    lanes = lanes + 1;
                    if (((TextField) node).getText().isEmpty()) {
                        AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error!",
                                "No value in " + id + " nr: " + lanes);
                        return;
                    }
                }
            }
        }
    }

    private List<DockModel> getAllDocks() {
        return anchorPane
                .getChildren()
                .stream()
                .filter(innerNode -> innerNode instanceof TextField)
                .filter(innerNode -> innerNode.getId().contains(DOCK))
                .map(node -> {
                    final DockModel dockModel = new DockModel();
                    dockModel.setId(node.getId());
                    dockModel.setValue(((TextField) node).getText());

                    return dockModel;
                })
                .collect(Collectors.toList());
    }

    public void handleLoadFileButtonAction(ActionEvent actionEvent) throws IOException {

        final FileHandler fileHandler = new FileHandler(window);

        String s = fileHandler.loadFileToString();

        final TemplateConverter templateConverter = new TemplateConverter();




        final String result = templateConverter.convert(getAllDocks(), s);

        fileHandler.saveFile(result);
    }
}
