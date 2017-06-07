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
     * Private constructor to avoid object creation from outside classes.
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
     * @return the instance of DatabaseAccess
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
        deviceValues.put("client_id", device.getClient_id());
        deviceValues.put("pin_number", device.getPinNumber());

        database.insert("device", null, deviceValues);
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
            device.setClient_id(cursor.getInt(4));
            device.setPinNumber(cursor.getInt(5));

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
            device.setClient_id(cursor.getInt(4));
            device.setPinNumber(cursor.getInt(5));
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
     */
    public void updateDevice(DeviceModel oldDevice, DeviceModel newDevice) {
        ContentValues deviceValues = new ContentValues();
        deviceValues.put("title", newDevice.getTitle());
        deviceValues.put("description", newDevice.getDescription());
        deviceValues.put("type_id", newDevice.getType());
        deviceValues.put("client_id", newDevice.getClient_id());
        deviceValues.put("pin_number", newDevice.getPinNumber());
        database.update("device", deviceValues, "id = ?", new String[]{String.valueOf(oldDevice.getId())});
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
     * Check if there is any devices in the database
     *
     * @return true if any and false if not.
     */
    public boolean anyDevices() {
        Cursor cursor = database.rawQuery("SELECT * FROM device", null);
        if(cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        return true;
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

            Cursor device_ids_cursor = database.rawQuery("SELECT device_id FROM scenario_device_binding WHERE scenario_id = ?", new String[] {String.valueOf(scenario.getId())});
            device_ids_cursor.moveToFirst();
            List<Integer> device_ids = new ArrayList();
            while (!device_ids_cursor.isAfterLast()) {
                device_ids.add(device_ids_cursor.getInt(0));
                device_ids_cursor.moveToNext();
            }
            device_ids_cursor.close();

            scenario.setDevice_ids(device_ids);
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
    }


    /**
     * Insert a client
     *
     * @param client the ClientModel to insert
     */
    public void insertClient(ClientModel client) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("title", client.getTitle());
        clientValues.put("ip_address", client.getIp_address());
        database.insert("client", null, clientValues);
    }

    /**
     * Get all clients from database
     *
     * @return a list of ClientModel's
     */
    public List<ClientModel> getClients() {
        List<ClientModel> clients = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM client", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ClientModel client = new ClientModel();
            client.setId(cursor.getInt(0));
            client.setTitle(cursor.getString(1));
            client.setIp_address(cursor.getString(2));

            clients.add(client);
            cursor.moveToNext();
        }
        cursor.close();

        return clients;
    }

    /**
     * Get a single client from the database
     *
     * @param id the client's id
     * @return a ClientModel
     */
    public ClientModel getClient(int id) {
        Cursor cursor = database.rawQuery("SELECT * FROM client WHERE id = ?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        ClientModel client = new ClientModel();
        client.setId(id);
        client.setTitle(cursor.getString(1));
        client.setIp_address(cursor.getString(2));
        cursor.close();
        return client;
    }

    /**
     * Delete a client from the database
     *
     * @param id the id of the client
     */
    public void deleteClient(int id) {
        database.delete("client", "id = ?", new String[]{String.valueOf(id)});
    }

    /**
     * Update a client from the database
     *
     * @param client the ClientModel to update
     */
    public void updateClient(ClientModel client) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("title", client.getTitle());
        clientValues.put("ip_address", client.getIp_address());
        database.update("client", clientValues, "id = ?", new String[]{String.valueOf(client.getId())});
    }

    /**
     * Check if there is any clients in the database
     *
     * @return true if any and false if not.
     */
    public boolean anyClients() {
        Cursor cursor = database.rawQuery("SELECT * FROM client", null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }

        return true;
    }

    /**
     * This is just to clear the database when testing.
     */
    public void clearDatabase() {
        database.delete("client", null, null);
        database.delete("device", null, null);
        database.delete("scenario", null, null);
        database.delete("favorites", null, null);
        database.delete("scenario_device_binding", null, null);
    }
}
