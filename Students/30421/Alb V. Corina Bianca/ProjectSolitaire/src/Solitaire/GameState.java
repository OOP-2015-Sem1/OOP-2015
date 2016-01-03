package Solitaire;

import java.util.ArrayList;

import Cards.ClassicCard;
import Cards.ClassicDeck;
import Cards.Move;
import Cards.Stack;
import Cards.Value;

public class GameState {

    protected   ClassicDeck         deck;
    protected   Stack               revealedCards;
    protected   SolitaireStack[]    solStack;
    protected   SequentialStack[]   seqStack;

    protected   Stack src;
    protected   Stack dst;
    protected   Stack curr;
	
    // Is this game won?
    public boolean isGameWon() {
        boolean gameWon = this.deck.isEmpty() && this.revealedCards.isEmpty();
        if( gameWon )
            for( int i = 0; i < Solitaire.FREED_CARDS_CNT && gameWon; i++ )
                gameWon = gameWon && solStack[ i ].isEmpty();
        return( gameWon );
    }

    public boolean equals(Object obj) {
        if( obj == null || !( obj instanceof GameState ) )
            return( false );

        GameState gs = (GameState)obj;
        if (this.deck.getCards().size()!=gs.deck.getCards().size() ||
                this.revealedCards.getCards().size()!= gs.revealedCards.getCards().size()){
            return false;
        }
        for (int i = 0; i < Solitaire.FREED_CARDS_CNT; i++) {
            if (this.solStack[i].cardCount() != gs.solStack[i].cardCount()){
                return false;
            }
        }
        for (int i = 0; i < Solitaire.FOUNDATION_STACK; i++) {
            if (this.seqStack[i].cardCount() != gs.seqStack[i].cardCount()){
                return false;
            }
        }

        return true;
    }

    public ArrayList<GameState> legalMoves (){
        ArrayList<GameState>    legalGs = new ArrayList<GameState>();
        ClassicCard currentCard,c;
        Stack   curr;
        Stack   currStack;
        Stack   src;
        Stack   dst;
        int     j=-1;

        // Set all cards to not legal
        for (int i = 0; i < Solitaire.FREED_CARDS_CNT; i++) {
            for (int k = 0; k < this.solStack[i].cardCount(); k++) {
                ClassicCard cc=(ClassicCard) (this.solStack[i].elementAt(k));
                cc.setLegal(false);
            }
        }
        for (int i = 0; i < Solitaire.FOUNDATION_STACK; i++) {
            for (int k = 0; k < this.seqStack[i].cardCount(); k++) {
                ClassicCard cc=(ClassicCard) (this.seqStack[i].elementAt(k));
                cc.setLegal(false);
            }
        }
        for (int i = 0; i < this.revealedCards.cardCount(); i++) {
            ClassicCard cc=(ClassicCard) (this.revealedCards.elementAt(i));
            cc.setLegal(false);
        }
        for (int i = 0; i < this.deck.cardCount(); i++) {
            ClassicCard cc=(ClassicCard) (this.deck.elementAt(i));
            cc.setLegal(false);
        }

        // Check Solitaire Stacks Legal Moves
        for (int i = 0; i < Solitaire.FREED_CARDS_CNT; i++) {
            j=this.solStack[i].firstFaceUp();
            // Find 1st turned over card in stack
            if (j!=-1){
                // Can this card be moved to another solitaire stack slot?
                c = ((ClassicCard)this.solStack[i].elementAt(j));
                legalSolToSol(legalGs, c, i);
                // Can this card be moved to a sequential stack?
                legalSolToSeq(legalGs, c, i);

                if (j+1!=this.solStack[i].cardCount()){
                    // If 1st turned over not equal top card then try legal moves on top card
                    j=this.solStack[i].cardCount()-1;
                    c = ((ClassicCard)this.solStack[i].elementAt(j));
                    // Can this card be moved to a sequential stack?
                    legalSolToSeq(legalGs, c, i);
                }
            }
        }

        c=(ClassicCard)this.revealedCards.top();
        // Check Revealed cards legal move to sequential stacks
        legalRevToSeq(legalGs, c);
        // Check Revealed cards legal moves to solitaire stacks
        legalRevToSol(legalGs, c);

        return legalGs;
    }

    // Check if card can be moved from one sol stack to another
    private void legalSolToSol(ArrayList<GameState> legalGs, ClassicCard c, int i) {
        if (c.getValue()== Value.V_ACE){
            // Never move an ace between sol stacks only to a sequential stack
            return;
        }
        Stack curr;
        Stack currStack;
        Stack src;
        Stack dst;
        src = this.solStack[ i ];
        currStack = src.pop( c ); 
        currStack.reverse(); 
        curr = currStack;   
        if( curr != null )
            curr.reverse();
        for (int j2 = 0; j2 < Solitaire.FREED_CARDS_CNT; j2++) {
            if (j2!=i){
                dst = this.solStack[ j2 ];
                if( dst != null && dst.isValid( curr ) ) { 
                    // Only consider the move if it turns over a card or empties a stack
                    if (src.isEmpty() | (!src.isEmpty() && src.top().isFaceDown())){
                        // Don't consider if source stack after move is empty but destination stack before move was empty
                        // i.e. moving a king stack to an empty stack.
                        if (!(src.isEmpty() && dst.isEmpty())){
                            // Move is legal so apply move and store result gamestate in legal games states
                            ClassicCard cOut = ((ClassicCard)curr.elementAt(curr.cardCount()-1));
                            cOut.setLegal(true);
                        }
                    }
                }
            }
        }
        // put cards back on src stack
        while(!curr.isEmpty())
            src.push( curr.pop() );
    }

    // Check if card can be moved to a sequential stack
    private void legalSolToSeq(ArrayList<GameState> legalGs, ClassicCard c, int i) {
        Stack curr;
        Stack currStack;
        Stack src;
        Stack dst;
        src = this.solStack[ i ];
        currStack = src.pop( c ); 
        currStack.reverse(); 
        curr = currStack;   
        if( curr != null )
            curr.reverse();
        if (curr.cardCount()>2){
            // Can't move more than one card to sequential stack
            while(!curr.isEmpty())
                src.push( curr.pop() );
            return;
        }
        for (int j2 = 0; j2 < Solitaire.FOUNDATION_STACK; j2++) {
            dst = this.seqStack[ j2 ];
            if( dst != null && dst.isValid( curr ) ) { 
                // Move is legal so apply move and store result gamestate in legal games states
                ClassicCard cOut = ((ClassicCard)curr.elementAt(curr.cardCount()-1));

                cOut.setLegal(true);
                while(!curr.isEmpty())
                    src.push( curr.pop() );
                // if can go on one sequential stack no need to check the others
                return;
            }
        }
        // put cards back on src stack
        while(!curr.isEmpty())
            src.push( curr.pop() );
    }

    // Check if revealed card can be place on one of the solitaire stacks
    private void legalRevToSol(ArrayList<GameState> legalGs, ClassicCard c) {
        Stack curr;
        Stack currStack;
        Stack src;
        Stack dst;
        if (this.revealedCards.isEmpty())
            return;
        src = this.revealedCards;
        currStack = src.pop( c ); 
        currStack.reverse(); 
        curr = currStack;   
        if( curr != null )
            curr.reverse();
        for (int j2 = 0; j2 < Solitaire.FREED_CARDS_CNT; j2++) {
            dst = this.solStack[ j2 ];
            if( dst != null && dst.isValid( curr ) ) { 
                // Move is legal so apply move and store result gamestate in legal games states
                ClassicCard cOut = ((ClassicCard)curr.elementAt(0));
                cOut.setLegal(true);
            }
        }
        // put cards back on src stack
        while(!curr.isEmpty())
            src.push( curr.pop() );
    }

    // Check if revealed card can be place on one of the sequential stacks
    private void legalRevToSeq(ArrayList<GameState> legalGs, ClassicCard c) {
        Stack curr;
        Stack currStack;
        Stack src;
        Stack dst;
        if (this.revealedCards.isEmpty())
            return ;
        src = this.revealedCards;
        currStack = src.pop( c ); 
        currStack.reverse(); 
        curr = currStack;   
        if( curr != null )
            curr.reverse();
        if (curr.cardCount()>2){
            // Can't move more than one card to sequential stack
            while( !curr.isEmpty() )
                src.push( curr.pop() );
            return ;
        }
        for (int j2 = 0; j2 < Solitaire.FOUNDATION_STACK; j2++) {
            dst = this.seqStack[ j2 ];
            if( dst != null && dst.isValid( curr ) ) { 
                // Only consider the move if it turns over a card or empties a stack
                // Move is legal so apply move and store result gamestate in legal games states
                ClassicCard cOut = ((ClassicCard)curr.elementAt(0));
                cOut.setLegal(true);
                while(!curr.isEmpty())
                    src.push( curr.pop() );
                // if can go on one sequential stack no need to check the others
                return ;
            }
        }
        // put cards back on src stack
        while(!curr.isEmpty())
            src.push( curr.pop() );
        return ;
    }

    // Save the state of the solitaire game - make deep copies of objects
    // This is important otherwise the saved objects will get corrupted as the 
    // game plays out i.e. turned face up etc.
    // This is used to undo moves
    public GameState(   ClassicDeck deck,
                        Stack revealedCards,
                        SolitaireStack[] solStack,
                        SequentialStack[] seqStack,
                        Stack src,
                        Stack dst,
                        Stack curr) {
        super();
        this.deck=new ClassicDeck();
        for (int i = 0; i < deck.cardCount(); i++) {
            ClassicCard currentCard = ((ClassicCard)deck.elementAt(i));
            ClassicCard c=new ClassicCard(currentCard);
            this.deck.push(c);
        }
        this.revealedCards=new Stack();
        for (int i = 0; i < revealedCards.cardCount(); i++) {
            ClassicCard currentCard = ((ClassicCard)revealedCards.elementAt(i));
            ClassicCard c=new ClassicCard(currentCard);
            this.revealedCards.push(c);
        }

        this.solStack=new SolitaireStack[Solitaire.FREED_CARDS_CNT];
        for (int i = 0; i < Solitaire.FREED_CARDS_CNT; i++) {
            this.solStack[ i ] = new SolitaireStack();
            for (int j = 0; j < solStack[i].cardCount(); j++) {
                ClassicCard currentCard = ((ClassicCard)solStack[i].elementAt(j));
                ClassicCard c=new ClassicCard(currentCard);
                this.solStack[i].push(c);
            }
        }
        this.seqStack=new SequentialStack[Solitaire.FOUNDATION_STACK];
        for (int i = 0; i < Solitaire.FOUNDATION_STACK; i++) {
            this.seqStack[ i ] = new SequentialStack();
            for (int j = 0; j < seqStack[i].cardCount(); j++) {
                ClassicCard currentCard = ((ClassicCard)seqStack[i].elementAt(j));
                ClassicCard c=new ClassicCard(currentCard);
                this.seqStack[i].push(c);
            }
        }
        this.src = src;
        this.dst = dst;
        this.curr = curr;
    }

    // Save the state of the solitaire game
    // do NOT make a deep copy
    // this is used in the case of legal move generation
    public GameState( ClassicDeck deck, Stack revealedCards, SolitaireStack[] solStack, SequentialStack[] seqStack) {
        super();
        this.deck=deck;
        this.revealedCards=revealedCards;
        this.solStack=solStack;
        this.seqStack=seqStack;
    }   

    // Restore the game state to the previous state
    // In the case of undo this would be before the last move
    public void restoreGameState( ClassicDeck deck, Stack revealedCards, SolitaireStack[] solStack, SequentialStack[] seqStack, Move move) {
        while( !revealedCards.isEmpty() ){
            revealedCards.pop();
        }
        for (int i = 0; i < this.revealedCards.cardCount(); i++) {
            ClassicCard currentCard = ((ClassicCard)this.revealedCards.elementAt(i));
            ClassicCard c=new ClassicCard(currentCard);
            revealedCards.push(c);

        }
        while(!deck.isEmpty()){
            deck.pop();
        }
        for (int i = 0; i < this.deck.cardCount(); i++) {
            ClassicCard currentCard  = ((ClassicCard)this.deck.elementAt(i));
            ClassicCard c=new ClassicCard(currentCard);
            deck.push(c);
        }
        for (int i = 0; i < Solitaire.FOUNDATION_STACK; i++) {
            while( !seqStack[i].isEmpty()){
                seqStack[i].pop();
            }
            for (int j = 0; j < this.seqStack[i].cardCount(); j++) {
                ClassicCard currentCard  = ((ClassicCard)this.seqStack[i].elementAt(j));
                ClassicCard c=new ClassicCard(currentCard);
                seqStack[i].push(c);
            }
        }
        for (int i = 0; i < Solitaire.FREED_CARDS_CNT; i++) {
            while( !solStack[i].isEmpty()){
                solStack[i].pop();
            }

            for (int j = 0; j < this.solStack[i].cardCount(); j++) {
                ClassicCard currentCard  = ((ClassicCard)this.solStack[i].elementAt(j));
                ClassicCard c=new ClassicCard(currentCard);
                solStack[i].push(c);
            }
        }
    }
}
