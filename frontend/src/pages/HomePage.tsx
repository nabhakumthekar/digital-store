import { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';

import { Product } from '../types';
import UploadCsv from '../components/UploadCsv';
import DownloadExcel from '../components/DownloadExcel';
import ProductTable from '../components/ProductTable';
import { fetchProducts } from '../api/Api';

function HomePage() {
  const [products, setProducts] = useState<Product[]>([]);
  const [isLastPage, setIsLastPage] = useState(false);
  const [page, setPage] = useState(0);
  const [filter, setFilter] = useState('');

  const location = useLocation();
  const navigate = useNavigate();
  const { username, role, password } = location.state || {};

  useEffect(() => {
    handleFetchProducts();
  }, [page]);

  const handleFetchProducts = async () => {
    fetchProducts({ page, filter }).then((data) => {
      setProducts(data.content);
      setIsLastPage(data.last);
    }).catch((err) => {
      throw err;
    });
  };

  const handleLogout = () => {
    navigate('/');
  }

  return (
    <Grid container spacing={2} direction="column" alignItems="center" justifyContent="center" style={{ minHeight: '100vh' }}>
      <Typography variant="h5" gutterBottom>
        Digital Store Dashboard
      </Typography>
      <Typography variant="h6" gutterBottom>
        Welcome, {username} ({role})
      </Typography>

      {role === 'ADMIN' && <UploadCsv username={username} password={password} onUploadSuccess={handleFetchProducts} />}
      <DownloadExcel filter={filter} />

      <Grid container spacing={2} direction="row" alignItems="center" justifyContent="center">
        <Typography variant="h6" gutterBottom>
          Product List
        </Typography>
        <TextField
          placeholder="Filter by category"
          value={filter}
          onChange={(e) => setFilter(e.target.value)}
        />
        <Button variant="contained" onClick={handleFetchProducts}>Search</Button>
      </Grid>
      <Grid container spacing={2} direction="row" alignItems="center" justifyContent="center">
        <ProductTable products={products} />
      </Grid>
      <Grid container spacing={2} direction="row" alignItems="center" justifyContent="center">
        <Button onClick={() => setPage(page - 1)} disabled={page <= 0}>Prev</Button>
        <Button onClick={() => setPage(page + 1)} disabled={isLastPage}>Next</Button>
      </Grid>
      <Button variant="contained" onClick={handleLogout}>Logout</Button>
    </Grid>
  );
}

export default HomePage;