import java.io.Serializable;

public class Result implements Serializable, Comparable<Result> {
    private String nazwa;
    private double wynik;

    public Result(String nazwa, double wynik){
        this.nazwa=nazwa;
        this.wynik=wynik;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public double getWynik() {
        return wynik;
    }

    public void setWynik(int wynik) {
        this.wynik = wynik;
    }

    @Override
    public String toString() {
        return nazwa +" ------ " + wynik;
    }

    @Override
    public int compareTo(Result o) {
        return Double.compare(o.wynik,this.wynik);
    }
}
