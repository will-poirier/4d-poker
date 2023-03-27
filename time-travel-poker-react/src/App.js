import './App.css';
import Card from './Card';
import Hand from './Hand';
import Player from './Player';

function App() {
    // debug init code
    let cards = [new Card({suit: 'Spades', value:'14'}), new Card({suit: 'Hearts', value:'9'}), new Card({suit: 'Diamonds', value:'11'}), new Card({suit: 'Clubs', value:'2'}), new Card({suit: 'Hearts', value:'3'})]
    let hand = new Hand({cards: cards});
    let pocket = new Hand({cards: cards});

    return (
        <div className="App">
            <Player name='Will' cash={30} hand={hand} pocket={pocket}></Player>
        </div>
    );
}

export default App;
