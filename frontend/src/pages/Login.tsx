import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import Alert from '@mui/material/Alert';

import { login } from '../api/Api';

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent) => {
    if (username === "" || password === "") {
      return setMessage('Please enter username and password'); 
    } else {
      login({ username, password }).then((data) => {
        if (data.status === 401) {
          return setMessage(data.message);
        }
        navigate('/homePage', { state: { username, password, role: data.role } });
      }).catch(() => {
        setMessage('Error connecting to server');
      });
    }
  };

  const handleRegister = (e: React.FormEvent) => {
    navigate('/register');
  };

  return (
    <Grid container spacing={2} direction="column" alignItems="center" justifyContent="center" style={{ minHeight: '100vh' }}>
      <h2>Login</h2>
      <TextField
        placeholder="Username"
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
        <Button variant="contained" onClick={handleLogin}>Login</Button>
        <Button variant="outlined" onClick={handleRegister}>Create User</Button>
      </Grid>
      {message !== "" && <Alert severity="error">{message}</Alert>}
    </Grid >
  );
}

export default Login;
