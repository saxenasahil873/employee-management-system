const api = '/api';
let employees = [], departments = [], roles = [];
const $ = id => document.getElementById(id);

async function request(path, options = {}) {
    const response = await fetch(api + path, { headers: {'Content-Type':'application/json'}, ...options });
    if (response.status === 204) return null;
    const body = await response.json();
    if (!response.ok) throw new Error(body.message || 'Request failed');
    return body;
}

function money(value) { return new Intl.NumberFormat('en-IN', {style:'currency', currency:'INR', maximumFractionDigits:0}).format(value); }
function render() {
    const query = $('search').value.toLowerCase().trim();
    const rows = employees.filter(e => `${e.firstName} ${e.lastName} ${e.email} ${e.jobTitle} ${e.departmentName}`.toLowerCase().includes(query));
    $('employee-rows').innerHTML = rows.map(e => `<tr><td><div class="person"><span class="avatar">${e.firstName[0]}${e.lastName[0]}</span><div><div class="name">${e.firstName} ${e.lastName}</div><div class="email">${e.email}</div></div></div></td><td>${e.jobTitle}<br><span class="pill">${e.roleName}</span></td><td>${e.departmentName}</td><td>${e.hireDate}</td><td>${money(e.salary)}</td><td><button class="menu" data-edit="${e.id}" aria-label="Edit employee">Edit</button><button class="menu danger" data-delete="${e.id}" aria-label="Delete employee">Delete</button></td></tr>`).join('');
    $('empty').style.display = rows.length ? 'none' : 'block';
    $('total-count').textContent = employees.length;
    $('department-count').textContent = departments.length;
    $('average-salary').textContent = employees.length ? money(employees.reduce((sum, e) => sum + Number(e.salary), 0) / employees.length) : '₹0';
    document.querySelectorAll('[data-edit]').forEach(button => button.addEventListener('click', () => openForm(employees.find(e => e.id == button.dataset.edit))));
    document.querySelectorAll('[data-delete]').forEach(button => button.addEventListener('click', async () => {
        const employee = employees.find(e => e.id == button.dataset.delete);
        if (!employee || !confirm(`Delete ${employee.firstName} ${employee.lastName}?`)) return;
        try { await request(`/employees/${employee.id}`, {method:'DELETE'}); await load(); } catch (error) { alert(error.message); }
    }));
}
function fillSelects() {
    $('departmentId').innerHTML = departments.map(d => `<option value="${d.id}">${d.name}</option>`).join('');
    $('roleId').innerHTML = roles.map(r => `<option value="${r.id}">${r.name}</option>`).join('');
}
function openForm(employee) {
    $('modal').classList.remove('hidden'); $('form-title').textContent = employee ? 'Edit employee' : 'Add employee'; $('employee-id').value = employee?.id || '';
    ['firstName','lastName','email','phone','jobTitle','salary','hireDate','departmentId','roleId'].forEach(field => $(field).value = employee?.[field] ?? '');
}
function closeForm() { $('modal').classList.add('hidden'); $('employee-form').reset(); $('form-error').textContent = ''; }
async function load() { [employees, departments, roles] = await Promise.all([request('/employees'), request('/departments'), request('/roles')]); fillSelects(); render(); }
$('open-create').addEventListener('click', () => openForm()); $('close-modal').addEventListener('click', closeForm); $('cancel').addEventListener('click', closeForm); $('search').addEventListener('input', render);
$('employee-form').addEventListener('submit', async event => { event.preventDefault(); $('form-error').textContent = ''; const id = $('employee-id').value; const payload = Object.fromEntries(new FormData(event.target)); payload.salary = Number(payload.salary); payload.departmentId = Number(payload.departmentId); payload.roleId = Number(payload.roleId); try { await request(id ? `/employees/${id}` : '/employees', {method:id ? 'PUT' : 'POST', body:JSON.stringify(payload)}); closeForm(); await load(); } catch (error) { $('form-error').textContent = error.message; } });
load().catch(error => { $('form-error').textContent = error.message; });
