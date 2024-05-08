package engine.data.comparatori;

import engine.data.Salvataggio;

import java.util.Comparator;

public class CompareByNumeroMosse implements Comparator<Salvataggio> {
    @Override
    public int compare(Salvataggio o1, Salvataggio o2) {
        return o2.getNumeroMosse() - o1.getNumeroMosse();
    }
}
