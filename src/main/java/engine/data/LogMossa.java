package engine.data;

import org.json.JSONObject;

public class LogMossa {

    int oldRiga, oldColonna, newRiga, newColonna;
    String codPezzoMosso, codPezzoMangiato;

    int numeroMossa;

    public LogMossa(int oldRiga, int oldColonna, int newRiga, int newColonna, String codPezzoMangiato, String codPezzoMosso, int numeroMossa) {
        this.oldRiga = oldRiga;
        this.oldColonna = oldColonna;
        this.newRiga = newRiga;
        this.newColonna = newColonna;
        this.codPezzoMangiato = codPezzoMangiato;
        this.codPezzoMosso = codPezzoMosso;
        this.numeroMossa = numeroMossa;
    }



    public int getOldRiga() {
        return oldRiga;
    }

    public void setOldRiga(int oldRiga) {
        this.oldRiga = oldRiga;
    }

    public int getOldColonna() {
        return oldColonna;
    }

    public void setOldColonna(int oldColonna) {
        this.oldColonna = oldColonna;
    }

    public int getNewRiga() {
        return newRiga;
    }

    public void setNewRiga(int newRiga) {
        this.newRiga = newRiga;
    }

    public int getNewColonna() {
        return newColonna;
    }

    public void setNewColonna(int newColonna) {
        this.newColonna = newColonna;
    }

    public String getCodPezzoMangiato() {
        return codPezzoMangiato;
    }

    public void setCodPezzoMangiato(String codPezzoMangiato) {
        this.codPezzoMangiato = codPezzoMangiato;
    }

    public String getCodPezzoMosso() {
        return codPezzoMosso;
    }

    public void setCodPezzoMosso(String codPezzoMosso) {
        this.codPezzoMosso = codPezzoMosso;
    }

    public void setNumeroMossa(int numeroMossa) {
        this.numeroMossa = numeroMossa;
    }

    public int getNumeroMossa() {
        return numeroMossa;
    }

    @Override
    public String toString() {
        return
                "oldRiga=" + oldRiga +
                ", oldColonna=" + oldColonna +
                ", newRiga=" + newRiga +
                ", newColonna=" + newColonna +
                ", codPezzoMosso='" + codPezzoMosso + '\'' +
                ", codPezzoMangiato='" + codPezzoMangiato + '\'' +
                ", numeroMossa=" + numeroMossa + "\n";
    }
    public JSONObject toJson(){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("oldRiga", oldRiga);
        jsonObj.put("oldColonna", oldColonna);
        jsonObj.put("newRiga", newRiga);
        jsonObj.put("newColonna", newColonna);
        jsonObj.put("codPezzoMosso",codPezzoMosso);
        jsonObj.put("codPezzoMangiato", codPezzoMangiato);
        jsonObj.put("numeroMossa", numeroMossa);
        return jsonObj;
    }
}
