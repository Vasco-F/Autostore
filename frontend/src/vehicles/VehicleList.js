import React, {Component} from "react";

import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

import Typography from '@mui/material/Typography';

import {Container, Button} from "@mui/material"

import ButtonAppBar from "./ButtonAppBar";
import { Box } from "@mui/system";

class VehicleList extends Component {

    constructor(props){
        super(props);
        this.state = {
            vehicles: []
        };
        this.remove = this.remove.bind(this);
    }

    componentDidMount(){
        fetch("/vehicles")
            .then(response => response.json())
            .then(data => this.setState({vehicles: data}));
    }

    async remove(id) {
        await fetch(`/vehicles/${id}`, {
            method: "DELETE",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        }).then(() => {
            let updatedVehicles = [...this.state.vehicles].filter( i => i.vehicleId !== id);
            this.setState({vehicles: updatedVehicles});
        })
    }

    render(){
        const {vehicles, isLoading} = this.state;
        
        if(isLoading){
            return <p>Loading...</p>
        }

        return (
            <div>
                <ButtonAppBar/>
                <Container fluid>
                    <Box sx={{ width: '100%', maxWidth: 500 , p: 1}}>
                        <Typography variant="h4" component="div" gutterBottom>
                            Vehicles List
                        </Typography>
                    </Box>
                    <TableContainer component={Paper}>
                        <Table sx={{ minWidth: 650 }} aria-label="simple table">
                            <TableHead>
                            <TableRow>
                                <TableCell align="left" sx={{fontWeight: "bold"}}>Manufacturer</TableCell>
                                <TableCell align="right" sx={{fontWeight: "bold"}}>Model</TableCell>
                                <TableCell align="right" sx={{fontWeight: "bold"}}>Year</TableCell>
                                <TableCell align="right" sx={{fontWeight: "bold"}}>Consumption</TableCell>
                                <TableCell align="center" sx={{fontWeight: "bold"}}>Action</TableCell>
                            </TableRow>
                            </TableHead>
                            <TableBody>
                            {vehicles.map((vehicle) => (
                                <TableRow
                                key={vehicle.vehicleId}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell align="left">{vehicle.manufacturer}</TableCell>
                                    <TableCell align="right">{vehicle.model}</TableCell>
                                    <TableCell align="right">{vehicle.year}</TableCell>
                                    <TableCell align="right">{vehicle.consumption}</TableCell>
                                    <TableCell align="center">
                                        <Button variant="contained" color="primary" href={"/vehicles/" + vehicle.vehicleId}>View</Button>{" "}
                                        <Button variant="contained" color="error" onClick={() => this.remove(vehicle.vehicleId)}>Delete</Button>
                                    </TableCell>
                                </TableRow>
                            ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                    <Box sx={{mt: 2}}>
                        <Button variant="contained" href="/vehicles/new" color="success">Add Vehicle</Button>
                    </Box>                    
                </Container>
            </div>
        )
    }

}
export default VehicleList;

