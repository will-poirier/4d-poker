import React, { createRef } from 'react'

class Card extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            image: this.deriveImageUrl(),
            alt: this.deriveAltText()
        }
    }

    deriveImageUrl = () => {
        let image = '';
        switch (this.props.value) {
            case '11':
                image = this.props.suit.toLowerCase() + "_jack";
                break;
            case '12':
                image = this.props.suit.toLowerCase() + "_queen";
                break;
            case '13':
                image = this.props.suit.toLowerCase() + "_king";
                break;
            case '14':
                image = this.props.suit.toLowerCase() + "_ace";
                break;
            default:
                image = this.props.suit.toLowerCase() + "_" + this.props.value;
                break;
        }
        return './cardImages/svg_playing_cards/fronts/' + image + '.svg';
    }

    deriveAltText = () => {
        let alt = '';
        switch (this.props.value) {
            case 11:
                alt = "Jack of " + this.props.suit;
                break;
            case 12:
                alt = "Queen of " + this.props.suit;
                break;
            case 13:
                alt = "King of " + this.props.suit;
                break;
            case 14:
                alt = "Ace of " + this.props.suit;
                break;
            default:
                alt = this.props.value + " of " + this.props.suit;
                break;
        }
        return alt
    }

    render() {
        return (
            <div>
                <img src={this.state.image} alt={this.state.deriveAltText}></img>
            </div>
        )
    }
}

export default Card