/**
 * S.I.L - Info (Sinais em Libras para Informática)
 * Script Principal de Interatividade e UX
 */

document.addEventListener('DOMContentLoaded', () => {
    console.log("S.I.L - Sistema Iniciado com Sucesso.");

    // --- 1. BUSCA EM TEMPO REAL (Para list.html e glossario.html) ---
    const searchInput = document.getElementById('searchSinal');
    const sinalCards = document.querySelectorAll('.card-sinal');

    if (searchInput) {
        searchInput.addEventListener('input', (e) => {
            const term = e.target.value.toLowerCase();
            
            sinalCards.forEach(card => {
                const title = card.querySelector('h2, h3').textContent.toLowerCase();
                const description = card.querySelector('p').textContent.toLowerCase();
                
                // Se o termo estiver no título ou na descrição, mostra o card
                if (title.includes(term) || description.includes(term)) {
                    card.style.display = 'block';
                } else {
                    card.style.display = 'none';
                }
            });
        });
    }

    // --- 2. SEGURANÇA: CONFIRMAÇÃO DE EXCLUSÃO (Para profile.html e admin-list.html) ---
    const dangerForms = document.querySelectorAll('form[action*="remover"], form[action*="excluir"]');
    
    dangerForms.forEach(form => {
        form.addEventListener('submit', (e) => {
            const confirmacao = confirm("CUIDADO: Esta ação removerá o item permanentemente. Deseja continuar?");
            if (!confirmacao) {
                e.preventDefault(); // Cancela o envio do formulário
            }
        });
    });

    // --- 3. UX: ANIMAÇÃO DE CARDS (Visual de TI moderno) ---
    sinalCards.forEach(card => {
        card.addEventListener('mouseenter', () => {
            card.style.borderColor = '#007bff';
            card.style.boxShadow = '0 8px 15px rgba(0,0,0,0.1)';
        });
        
        card.addEventListener('mouseleave', () => {
            card.style.borderColor = '#ddd';
            card.style.boxShadow = 'none';
        });
    });

    // --- 4. FEEDBACK DE "COPIADO" (Para compartilhar termos) ---
    // Se você clicar no título de um sinal, ele copia o link da página
    const shareTitles = document.querySelectorAll('.detail-card h1');
    shareTitles.forEach(title => {
        title.style.cursor = 'pointer';
        title.title = "Clique para copiar o link do sinal";
        
        title.addEventListener('click', () => {
            navigator.clipboard.writeText(window.location.href);
            alert("Link do sinal copiado para a área de transferência!");
        });
    });
    // Lógica de Dark Mode (Ativada por um botão com id="darkModeToggle")
const toggleBtn = document.getElementById('darkModeToggle');
if (toggleBtn) {
    toggleBtn.addEventListener('click', () => {
        document.body.classList.toggle('dark-mode');
        const isDark = document.body.classList.contains('dark-mode');
        localStorage.setItem('theme', isDark ? 'dark' : 'light');
    });
}

// --- 5. LÓGICA DE DARK MODE (Exigência do Critério de Aceite) ---
    const btnDarkMode = document.getElementById('btn-dark-mode');
    const body = document.body;

    // Verifica se o usuário já tinha uma preferência salva
    if (localStorage.getItem('theme') === 'dark') {
        body.classList.add('dark-mode');
    }

    if (btnDarkMode) {
        btnDarkMode.addEventListener('click', () => {
            body.classList.toggle('dark-mode');
            
            // Salva a preferência para não perder ao mudar de página
            if (body.classList.contains('dark-mode')) {
                localStorage.setItem('theme', 'dark');
            } else {
                localStorage.setItem('theme', 'light');
            }
        });
    }



    
});