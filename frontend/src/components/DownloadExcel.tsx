import axios from 'axios';
import Grid from '@mui/material/Grid';
import Button from '@mui/material/Button';
import { downloadFile } from '../api/Api';

function DownloadExcel({filter}: {filter: string}) {

  const handleDownload = async () => {
    downloadFile(filter).then((res) => {
      console.log('File downloaded successfully');
    }).catch((err) => {
      throw err;
    });
  };

  return (
    <Grid container spacing={2} direction="row" alignItems="center" justifyContent="center">
      <h3>Download Product Excel</h3>
      <Button variant="contained" onClick={handleDownload}>Download</Button>
    </Grid>
  );
}

export default DownloadExcel;
