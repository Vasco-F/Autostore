import React, { Component } from 'react';
import '../App.css';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

// import car from '../images/background-1.jpg';

import ButtonAppBar from "./ButtonAppBar";

class Home extends Component {
    render() {
        return (
            <div>
                <ButtonAppBar/>
                <div styles={{ backgroundImage:"url('./images/background-1.jpg')" }}>
                    <Container fluid>
                        <Button color="link"><Link to="/vehicles">Vehicles</Link></Button>
                    </Container>
                </div>
            </div>
        );
    }
}
export default Home;