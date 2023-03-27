import React from 'react';

class Player extends React.Component {
    constructor(props) {
        super(props);
        this.state = ({
            name: props.name,
            hand: props.hand,
            pocket: props.pocket,
            cash: props.cash
        });
    }

    render = () => {
        return <div className='player'>
            <div className='playerCards'>
                {this.state.hand.render()}
                {this.state.pocket.render()}
            </div>
            <div className='playerInfo'>
                <p>Name: {this.state.name}</p>
                <p>Cash: {this.state.cash}</p>
            </div>
        </div>
    }
}

export default Player;