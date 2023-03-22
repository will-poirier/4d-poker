import React from 'react';

class Hand extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            cards: props.cards,
            size: 5
        }
    }

    render = () => {
        let cardRenders = []
        for (let index in this.state.cards) {
            cardRenders.push(this.state.cards[index].render())
        }
        return <div className='hand'>{cardRenders}</div>
    }
}

export default Hand;