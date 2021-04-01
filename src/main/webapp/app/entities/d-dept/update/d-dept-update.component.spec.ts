jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DDeptService } from '../service/d-dept.service';
import { IDDept, DDept } from '../d-dept.model';

import { DDeptUpdateComponent } from './d-dept-update.component';

describe('Component Tests', () => {
  describe('DDept Management Update Component', () => {
    let comp: DDeptUpdateComponent;
    let fixture: ComponentFixture<DDeptUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dDeptService: DDeptService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DDeptUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DDeptUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DDeptUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dDeptService = TestBed.inject(DDeptService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dDept: IDDept = { id: 456 };

        activatedRoute.data = of({ dDept });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dDept));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dDept = { id: 123 };
        spyOn(dDeptService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dDept });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dDept }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dDeptService.update).toHaveBeenCalledWith(dDept);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dDept = new DDept();
        spyOn(dDeptService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dDept });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dDept }));
        saveSubject.complete();

        // THEN
        expect(dDeptService.create).toHaveBeenCalledWith(dDept);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dDept = { id: 123 };
        spyOn(dDeptService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dDept });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dDeptService.update).toHaveBeenCalledWith(dDept);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
