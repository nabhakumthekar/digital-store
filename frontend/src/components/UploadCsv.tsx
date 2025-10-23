import React, { useState } from 'react';

import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import Alert from '@mui/material/Alert';

import { uploadFile } from '../api/Api';

function UploadCsv({ username, password, onUploadSuccess }: { username: string; password: string; onUploadSuccess: () => void }) {
  const [file, setFile] = useState<File | null>(null);
  const [message, setMessage] = useState("");

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      setFile(e.target.files[0]);
    }
  };

  const handleUpload = async () => {
    if (!file) return alert('Please select a CSV file');
    
    uploadFile(file, username, password).then((data) => {
      setMessage(data);
      onUploadSuccess();
    }).catch((err) => {
      throw err;
    });
  };

  return (
    <>
    <Grid container spacing={2} direction="row" alignItems="center" justifyContent="center">
      <h3>Upload Product CSV</h3>
      <input type="file" accept=".csv" onChange={handleFileChange} />
      <Button variant="contained" onClick={handleUpload}>Upload</Button>
    </Grid>
     {message !== "" && <Alert severity="success">{message}</Alert>}
    </>
  );
}

export default UploadCsv;
