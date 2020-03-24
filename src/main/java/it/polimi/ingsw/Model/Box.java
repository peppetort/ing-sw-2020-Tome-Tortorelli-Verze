package it.polimi.ingsw.Model;

/**
 * Rappresenta la cella della board sulla quale vengono posionati i pedoni e le varie costruzioni
 */
public class Box {

    /**
     * Rappresenta il tipo di costruzione presente sulla cella
     */
    private Block typeOfBuilding;
    /**
     * Rappresenta la presenza la pedina presente nella cella
     * Se sulla cella non è presente la pedina viene assegnato valore null
     */
    private Worker pawn;                //pedina

    /**
     * Rappresenta il costruttore della classe {@link Box}.
     * <p>
     * Il costruttore setta i parametri della cella inizializzando la presenza di una pedina {@link Worker}a null
     * e inizializzando il tipo di costruzione alla costruzione base {@link Block} a TERRAIN
     * </p>
     */
    public Box()
    {
        typeOfBuilding=Block.TERRAIN;
        pawn=null;
    }

    /**
     * @return il valore della pedina presente, null se non è presente nessuna pedina nella cella
     */
    public Worker getPawn(){return pawn;}

    /**
     * @param pawn per mettere il valore della pedina nella cella
     */
    public void setPawn(Worker pawn)
    {
            this.pawn=pawn;
    }

    /**
     * Rimuove la pedina dalla cella settandola a null
     */
    public void removePawn()
    {
        pawn=null;
    }
    /**
     * @return il valore la costruzione {@link Block} presente nel blocco
     */
    public Block getBlock(){return typeOfBuilding;}

    /**
     * @param b setta il valore del tipo di costruzione al valore passato
     */
    public void build(Block b) {
        this.typeOfBuilding = b;
    }

    /**
     * @return la presenza o meno di una pedina nella cella
     */
    public boolean isFree()
    {
        return pawn == null;
    }

    /*
    public boolean canBuild(Block block)
    {
        return typeOfBuilding != Block.DOME;
    }*/

    /**
     *
     * @param other cella sulla quale è calcolata la differenza
     * @return la differenza di livello tra la costruzione attuale e quella passata
     */
    public int getDifference(Box other){
        return this.typeOfBuilding.getValue()-other.typeOfBuilding.getValue();
    }

    /**
     *
     * @param other
     * @return se è possibile muovere la pedina nella cella passata controllando che non ci sia una cupola,
     * che non si può salire di un livello
     */
    public boolean compare(Box other){

        return this.getDifference(other) >= 0 || (this.getDifference(other) == -1 && other.typeOfBuilding.getValue() != 4);

    }
}
