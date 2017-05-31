package com.example.petersomersby.connhome.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter Somersby on 30-05-2017.
 */

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     *  Insert a device into the database
     *
     * @param device the device to be inserted
     */
    public void insertDevice(DeviceModel device) {
        ContentValues deviceValues = new ContentValues();
        deviceValues.put("title", device.getTitle());
        deviceValues.put("description", device.getDescription());
        deviceValues.put("type_id", device.getType());
        long device_id = database.insert("device", null, deviceValues);

        ContentValues clientDeviceValues = new ContentValues();
        clientDeviceValues.put("device_id", device_id);
        clientDeviceValues.put("pin_number", device.getPinNumber());
        clientDeviceValues.put("client_id", device.getClient_id());
        database.insert("client_device_binding", null, clientDeviceValues);
    }

    /**
     *  Read all devices from the database.
     *
     * @return a List of DeviceModel's
     */
    public List<DeviceModel> getDevices() {
        List<DeviceModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM device", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            DeviceModel device = new DeviceModel();
            device.setId(cursor.getInt(0));
            device.setTitle(cursor.getString(1));
            device.setDescription(cursor.getString(2));
            device.setType(cursor.getInt(3));
            list.add(device);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    /**
     *  Get a single device
     *
     * @param id the device's id
     * @return a DeviceModel
     */
    public DeviceModel getDevice(int id) {
        Cursor cursor = database.rawQuery("SELECT * FROM device WHERE id = ?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        DeviceModel device = new DeviceModel();
        while(!cursor.isAfterLast()) {
            device.setId(cursor.getInt(0));
            device.setTitle(cursor.getString(1));
            device.setDescription(cursor.getString(2));
            device.setType(cursor.getInt(3));
            cursor.moveToNext();
        }
        cursor.close();

        return device;
    }

    /**
     * Update the device details.
     *
     * @param oldDevice the old device to be replaced
     * @param newDevice the new device to replace
     * @param client_device_id the client_device_binding id to be updated
     */
    public void updateDevice(DeviceModel oldDevice, DeviceModel newDevice, int client_device_id) {
        ContentValues deviceValues = new ContentValues();
        deviceValues.put("title", newDevice.getTitle());
        deviceValues.put("description", newDevice.getDescription());
        deviceValues.put("type_id", newDevice.getType());
        int device_id = database.update("device", deviceValues, "id = ?", new String[]{String.valueOf(oldDevice.getId())});

        if (oldDevice.getPinNumber() != newDevice.getPinNumber()){
            ContentValues clientDeviceValues = new ContentValues();
            clientDeviceValues.put("device_id", device_id);
            clientDeviceValues.put("pin_number", newDevice.getPinNumber());
            clientDeviceValues.put("client_id", newDevice.getClient_id());
            database.update("client_device_binding",  clientDeviceValues, "id = ?", new String[]{toString().valueOf(client_device_id)});
        }
    }

    /**
     * Delete device
     *
     * @param id the id of the device to be deleted
     */
    public void deleteDevice(int id) {
        database.delete("device", "id = ?", new String[]{String.valueOf(id)});
    }

    /**
     * Insert a scenario into the database.
     *
     * @param scenario the scenario to be inserted
     */
    public void insertScenario(ScenarioModel scenario) {
        ContentValues scenarioValues = new ContentValues();
        scenarioValues.put("name", scenario.getName());
        scenarioValues.put("description", scenario.getDescription());
        long scenario_id = database.insert("scenario", null, scenarioValues);

        for (int id :
                scenario.getDevice_ids()) {
            ContentValues scenarioDeviceValues = new ContentValues();
            scenarioDeviceValues.put("scenario_id", scenario_id);
            scenarioDeviceValues.put("device_id", id);
            database.insert("scenario_device_binding", null, scenarioDeviceValues);
        }
    }

    /**
     * Read all scenarios from the database.
     *
     * @return a List of ScenarioModel's
     */
    public List<ScenarioModel> getScenarios() {
        List<ScenarioModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM scenario", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            ScenarioModel scenario = new ScenarioModel();
            scenario.setId(cursor.getInt(0));
            scenario.setName(cursor.getString(1));
            scenario.setDescription(cursor.getString(2));
            list.add(scenario);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    /**
     * Update the scenario details.
     *
     * @param oldScenario the old scenario to be replaced
     * @param newScenario the new scenario to replace
     */
    public void updateScenario(ScenarioModel oldScenario, ScenarioModel newScenario) {
        ContentValues scenarioValues = new ContentValues();
        scenarioValues.put("title", newScenario.getName());
        scenarioValues.put("description", newScenario.getDescription());
        int scenario_id = database.update("scenario", scenarioValues, "id = ?", new String[]{String.valueOf(oldScenario.getId())});

        for (int id :
                oldScenario.getDevice_ids()) {
            database.delete("scenario_device_binding", "id = ?", new String[]{String.valueOf(id)});
        }

        for (int device_id:
                newScenario.getDevice_ids()){
            ContentValues scenarioDeviceValues = new ContentValues();
            scenarioDeviceValues.put("device_id", device_id);
            scenarioDeviceValues.put("scenario_id", scenario_id);
            database.insert("scenario_device_binding", null, scenarioDeviceValues);
        }
    }

    /**
     * Delete the provided scenario.
     *
     * @param id the id of the scenario to delete
     */
    public void deleteScenario(int id) {
        database.delete("scenario", "id = ?", new String[]{String.valueOf(id)});
        database.close();
    }


}