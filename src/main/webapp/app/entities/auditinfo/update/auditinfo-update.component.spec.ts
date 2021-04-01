jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AuditinfoService } from '../service/auditinfo.service';
import { IAuditinfo, Auditinfo } from '../auditinfo.model';

import { AuditinfoUpdateComponent } from './auditinfo-update.component';

describe('Component Tests', () => {
  describe('Auditinfo Management Update Component', () => {
    let comp: AuditinfoUpdateComponent;
    let fixture: ComponentFixture<AuditinfoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let auditinfoService: AuditinfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AuditinfoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AuditinfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AuditinfoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      auditinfoService = TestBed.inject(AuditinfoService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const auditinfo: IAuditinfo = { id: 456 };

        activatedRoute.data = of({ auditinfo });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(auditinfo));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const auditinfo = { id: 123 };
        spyOn(auditinfoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ auditinfo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: auditinfo }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(auditinfoService.update).toHaveBeenCalledWith(auditinfo);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const auditinfo = new Auditinfo();
        spyOn(auditinfoService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ auditinfo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: auditinfo }));
        saveSubject.complete();

        // THEN
        expect(auditinfoService.create).toHaveBeenCalledWith(auditinfo);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const auditinfo = { id: 123 };
        spyOn(auditinfoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ auditinfo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(auditinfoService.update).toHaveBeenCalledWith(auditinfo);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
