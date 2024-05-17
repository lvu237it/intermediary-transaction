/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author User
 */
public class Correspond{
    private int oId;
    private int pId;

    public Correspond() {
    }

    public Correspond(int oId, int pId) {
        this.oId = oId;
        this.pId = pId;
    }

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    @Override
    public String toString() {
        return "Correspond{" + "oId=" + oId + ", pId=" + pId + '}';
    }
    
    
}
