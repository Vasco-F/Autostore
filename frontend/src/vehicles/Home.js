import React, { Component } from 'react';
import '../App.css';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

import ButtonAppBar from "./ButtonAppBar";

class Home extends Component {
    render() {
        return (
            <div>
                <ButtonAppBar/>
                <Container fluid>
                    <Button color="link"><Link to="/vehicles">Vehicles</Link></Button>
                </Container>
            </div>
        );
    }
}
export default Home;