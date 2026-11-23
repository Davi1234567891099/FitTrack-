const state = { auth: null, usuarioId: null };
const status = document.querySelector('#status');
const dashboard = document.querySelector('#dashboard');
const loginCard = document.querySelector('#login-card');

document.querySelector('#login-form').addEventListener('submit', async (event) => {
  event.preventDefault();
  const email = document.querySelector('#email').value;
  const senha = document.querySelector('#senha').value;
  try {
    const response = await fetch('/api/auth/login', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ email, senha }) });
    if (!response.ok) throw new Error('E-mail ou senha inválidos.');
    const usuario = await response.json();
    state.auth = `Basic ${btoa(`${email}:${senha}`)}`;
    state.usuarioId = usuario.usuarioId;
    status.textContent = `Olá, ${usuario.nome}. Seus dados estão protegidos.`;
    loginCard.hidden = true;
    dashboard.hidden = false;
    await carregarEvolucao();
  } catch (error) { status.textContent = error.message; }
});

document.querySelector('#atualizar').addEventListener('click', carregarEvolucao);

async function carregarEvolucao() {
  const response = await fetch(`/api/evolucao?usuarioId=${state.usuarioId}`, { headers: { Authorization: state.auth } });
  if (!response.ok) { status.textContent = 'Não foi possível carregar a evolução.'; return; }
  const dados = await response.json();
  preencherResumo(dados); preencherTabela(dados); desenharGrafico(dados);
}

function preencherResumo(dados) {
  document.querySelector('#total-registros').textContent = dados.length;
  document.querySelector('#total-exercicios').textContent = new Set(dados.map(item => item.exercicio)).size;
  const maior = dados.reduce((max, item) => Math.max(max, Number(item.cargaMaxima)), 0);
  document.querySelector('#maior-carga').textContent = maior ? `${formatarNumero(maior)} kg` : '-';
  document.querySelector('#sem-dados').hidden = dados.length > 0;
}

function preencherTabela(dados) {
  document.querySelector('#historico').innerHTML = dados.map(item => `<tr><td>${formatarData(item.data)}</td><td>${item.exercicio}</td><td>${formatarNumero(item.cargaMaxima)} kg</td><td>${item.totalSeries}</td></tr>`).join('');
}

function desenharGrafico(dados) {
  const canvas = document.querySelector('#grafico'); const ctx = canvas.getContext('2d');
  ctx.clearRect(0, 0, canvas.width, canvas.height); ctx.fillStyle = '#0d1729'; ctx.fillRect(0, 0, canvas.width, canvas.height);
  if (!dados.length) return;
  const margem = { x: 62, y: 35 }; const largura = canvas.width - margem.x - 28; const altura = canvas.height - margem.y - 56;
  const max = Math.max(...dados.map(item => Number(item.cargaMaxima)), 1); const cores = ['#47a5ff', '#65d6a7', '#f6bf5f', '#f57c9a'];
  ctx.strokeStyle = '#2a4169'; ctx.lineWidth = 1; ctx.font = '13px Arial'; ctx.fillStyle = '#a8b9d7';
  for (let passo = 0; passo <= 4; passo++) { const y = margem.y + altura - (altura * passo / 4); ctx.beginPath(); ctx.moveTo(margem.x, y); ctx.lineTo(margem.x + largura, y); ctx.stroke(); ctx.fillText(`${formatarNumero(max * passo / 4)} kg`, 8, y + 4); }
  const porExercicio = dados.reduce((mapa, item) => { (mapa[item.exercicio] ||= []).push(item); return mapa; }, {});
  Object.entries(porExercicio).forEach(([nome, pontos], indice) => {
    const cor = cores[indice % cores.length]; ctx.strokeStyle = cor; ctx.fillStyle = cor; ctx.lineWidth = 3; ctx.beginPath();
    pontos.forEach((ponto, i) => { const x = margem.x + (pontos.length === 1 ? largura / 2 : i * largura / (pontos.length - 1)); const y = margem.y + altura - (Number(ponto.cargaMaxima) / max * altura); i ? ctx.lineTo(x, y) : ctx.moveTo(x, y); }); ctx.stroke();
    pontos.forEach((ponto, i) => { const x = margem.x + (pontos.length === 1 ? largura / 2 : i * largura / (pontos.length - 1)); const y = margem.y + altura - (Number(ponto.cargaMaxima) / max * altura); ctx.beginPath(); ctx.arc(x, y, 4, 0, Math.PI * 2); ctx.fill(); });
    ctx.fillStyle = cor; ctx.fillText(nome, margem.x + indice * 155, 20);
  });
}

function formatarNumero(valor) { return Number(valor).toLocaleString('pt-BR', { maximumFractionDigits: 2 }); }
function formatarData(valor) { return new Date(`${valor}T00:00:00`).toLocaleDateString('pt-BR'); }
