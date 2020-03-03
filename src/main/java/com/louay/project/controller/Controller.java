package com.louay.project.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.*;

public class Controller {
    public TextField id;
    public TextField firstName;
    public TextField lastName;
    public TextField mi;
    public TextField address;
    public TextField state;
    public TextField city;
    public TextField telephone;
    public TextField email;
    public Button viewBtn;
    public Button insertBtn;
    public Button updateBtn;
    public Button clearBtn;
    public Label notification;

    private Connection connection;
    private Statement statement;
    private PreparedStatement insert;
    private PreparedStatement view;
    private PreparedStatement update;
    private PreparedStatement delete;

    private void connect(){
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost/lab18","root","root@root");
            System.out.println("connect accomplished");
        }
        catch (SQLException e){
            notification.setText(e.getMessage());
        }
    }

    public void clearOperation(ActionEvent actionEvent) {

        try {
            if (id.getText().isEmpty()){
                notification.setText("please insert ID stuff");
            }else {
                connect();
                String del = "DELETE from lab18.staff where idstaff = ?";
                delete = connection.prepareStatement(del);
                delete.setString(1, id.getText());
                delete.executeUpdate();
                notification.setText("Delete record done successfully.");
                connection.close();
            }
        }
        catch (SQLException e){
            notification.setText(e.getMessage());
        }

    }

    public void updateOperation(ActionEvent actionEvent) {
        try {
            if (id.getText().isEmpty()){
                notification.setText("please insert ID stuff");
            }else {
                connect();
                String query = "UPDATE lab18.staff set lastname=?,firstname=?,mi=?,address=?,city=?,state=?,telephone=?,email=? where idstaff=? ";
                update = connection.prepareStatement(query);
                update.setString(1, lastName.getText());
                update.setString(2, firstName.getText());
                update.setString(3, mi.getText());
                update.setString(4, address.getText());
                update.setString(5, city.getText());
                update.setString(6, state.getText());
                update.setString(7, telephone.getText());
                update.setString(8, email.getText());
                update.setString(9, id.getText());
                update.executeUpdate();
                notification.setText("Update record done successfully.");
                connection.close();
            }
        }
        catch (SQLException e){
            notification.setText(e.getMessage());
        }
    }

    public void viewOperation(ActionEvent actionEvent) {
        try {
            if (id.getText().isEmpty()){
                notification.setText("please insert ID stuff");
            }else {
                connect();
                view = connection.prepareStatement("select lastName, firstName, mi, address, city, state, telephone, email from lab18.staff where idStaff = ?");
                view.setString(1,id.getText());
                ResultSet rset = view.executeQuery();
                while (rset.next()){
                    lastName.setText(rset.getString(1));
                    firstName.setText(rset.getString(2));
                    mi.setText(rset.getString(3));
                    address.setText(rset.getString(4));
                    city.setText(rset.getString(5));
                    state.setText(rset.getString(6));
                    telephone.setText(rset.getString(7));
                    email.setText(rset.getString(8));
                    notification.setText("Text fields filled.");
                }
                connection.close();
            }
        }
        catch (SQLException e){
            notification.setText(e.getMessage());
        }
    }

    public void insertOperation(ActionEvent actionEvent) {
        if (id.getText().isEmpty()){
            notification.setText("ID field must fill");
        }else {
            try {
                connect();
                insert = connection.prepareStatement("insert into lab18.staff(idStaff, lastName, firstName, mi, address, city, state, telephone, email) values (?,?,?,?,?,?,?,?,?)");
                insert.setString(1, id.getText());
                insert.setString(2, lastName.getText());
                insert.setString(3, firstName.getText());
                insert.setString(4, mi.getText());
                insert.setString(5, address.getText());
                insert.setString(6, city.getText());
                insert.setString(7, state.getText());
                insert.setString(8, telephone.getText());
                insert.setString(9, email.getText());
                insert.executeUpdate();
                notification.setText("insert record done successfully.");
                connection.close();
            } catch (SQLException e) {
                notification.setText(e.getMessage());
            }
        }
    }

    public void initialize(){
        id.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (id.getText().length()>9){
                    id.setText(id.getText(0,9));
                }
            }
        });
        lastName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (lastName.getText().length()>15){
                    lastName.setText(lastName.getText(0,15));
                }
            }
        });
        firstName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (firstName.getText().length()>15){
                    firstName.setText(firstName.getText(0,15));
                }
            }
        });
        mi.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (mi.getText().length()>1){
                    mi.setText(mi.getText(0,1));
                }
            }
        });
        address.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (address.getText().length()>20){
                    address.setText(address.getText(0,20));
                }
            }
        });
        city.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (city.getText().length()>20){
                    city.setText(city.getText(0,20));
                }
            }
        });
        state.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (state.getText().length()>2){
                    state.setText(state.getText(0,2));
                }
            }
        });

        telephone.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")){
                    telephone.setText(newValue.replaceAll("[^\\d]",""));
                }
                if (telephone.getText().length()>10){
                    telephone.setText(telephone.getText(0,10));
                }
            }
        });
        email.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (email.getText().length()>40){
                    email.setText(email.getText(0,40));
                }
            }
        });
    }


    public void emptyField(ActionEvent actionEvent) {
        id.clear();
        lastName.clear();
        firstName.clear();
        mi.clear();
        address.clear();
        city.clear();
        state.clear();
        telephone.clear();
        email.clear();
    }


}
