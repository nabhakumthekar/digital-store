import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import Alert from '@mui/material/Alert';
import { register } from '../api/Api';

function Register() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('VIEWER');
    const [message, setMessage] = useState({ status: '', message: '' });
    const navigate = useNavigate();

    const handleRegister = async () => {

        if (username === "" || password === "") {
            setMessage({ status: '400', message: 'Username and password are required.' });
            return;
        }

        register({username, password, role}).then((res) => {
            if (res.status === 200) {
                setMessage({ status: res.status.toString(), message: 'Registration Successful! Please login.' });
            } else {
                setMessage({ status: res.status.toString(), message: res.message });
            }
        }).catch((err) => {
            setMessage({ status: '500', message: 'Registration Failed. Please try again.' });
        });
    };

    const handleBack = () => {
        navigate('/');
    };

    return (
        <Grid container spacing={2} direction="column" alignItems="center" justifyContent="center" style={{ minHeight: '100vh' }}>
            <h2>Register</h2>
            <TextField
                required
                id="outlined-required"
                label="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <TextField
                placeholder="Password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <Grid container spacing={2} direction="row" alignItems="center" justifyContent="center" style={{ marginTop: '20px' }}>
                <InputLabel id="demo-simple-select-label">Role</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={role}
                    label="Age"
                    onChange={(e) => setRole(e.target.value)}
                >
                    <MenuItem value="ADMIN">ADMIN</MenuItem>
                    <MenuItem value="VIEWER">VIEWER</MenuItem>
                </Select>
            </Grid>
            <Grid container spacing={2} direction="row" alignItems="center" justifyContent="center" style={{ marginTop: '20px' }}>
                <Button variant="contained" onClick={handleRegister}>Register</Button>
                <Button variant="outlined" onClick={handleBack}>Back</Button>
            </Grid>
            {message.status === '400' && <Alert severity="error">{message.message}</Alert>}
            {message.status === '200' && <Alert severity="success">{message.message}</Alert>}
        </Grid>
    );
}

export default Register;
