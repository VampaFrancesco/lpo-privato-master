package engine.data;

import org.json.JSONArray;
import java.io.FileWriter;
import java.util.ArrayList;


public class Logger {

    static int numMossa = 1;

    private static ArrayList<LogMossa> listaMosse = new ArrayList<>();

    public static ArrayList<LogMossa> getListaMosse() {
        return listaMosse;
    }

    /**
     * Questo metodo aggiunge una riga di mossa alla lista delle mosse
     *
     * @param oldRiga
     * @param oldColonna
     * @param newRiga
     * @param newColonna
     * @param codPezzoMosso
     * @param codPezzoMangiato
     */
    public static void addMossaLog(int oldRiga, int oldColonna, int newRiga, int newColonna, String codPezzoMosso, String codPezzoMangiato){
        LogMossa lm = new LogMossa(oldRiga, oldColonna, newRiga, newColonna, codPezzoMangiato, codPezzoMosso, numMossa++);
        listaMosse.add(lm);
        writeLog();
    }

    /**
     * Questo metodo viene invocato ogni volta che si aggiunge un logMossa
     * e si occupa di scrivere dentro il Log
     *
     */
    public static void writeLog(){
        JSONArray jsonarray = new JSONArray();
        for(LogMossa lg: listaMosse){
            jsonarray.put(lg.toJson());
        }
        String stringJson = jsonarray.toString();
        try (FileWriter file = new FileWriter("log_mosse.json")) {
            file.write(stringJson);
        }
        catch (Exception e) {
            return;
        }
    }
}
