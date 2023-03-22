import './App.css';
import Card from './Card';
import Hand from './Hand';

function App() {
    // debug init code
    let cards = [new Card({suit: 'Spades', value:'14'}), new Card({suit: 'Hearts', value:'9'})]

    return (
        <div className="App">
            <Card suit='Diamonds' value='11'></Card>
            <Hand cards={cards}></Hand>
        </div>
    );
}

export default App;
