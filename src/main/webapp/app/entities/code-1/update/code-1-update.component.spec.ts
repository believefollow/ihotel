jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { Code1Service } from '../service/code-1.service';
import { ICode1, Code1 } from '../code-1.model';

import { Code1UpdateComponent } from './code-1-update.component';

describe('Component Tests', () => {
  describe('Code1 Management Update Component', () => {
    let comp: Code1UpdateComponent;
    let fixture: ComponentFixture<Code1UpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let code1Service: Code1Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [Code1UpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(Code1UpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Code1UpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      code1Service = TestBed.inject(Code1Service);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const code1: ICode1 = { id: 456 };

        activatedRoute.data = of({ code1 });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(code1));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const code1 = { id: 123 };
        spyOn(code1Service, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ code1 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: code1 }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(code1Service.update).toHaveBeenCalledWith(code1);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const code1 = new Code1();
        spyOn(code1Service, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ code1 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: code1 }));
        saveSubject.complete();

        // THEN
        expect(code1Service.create).toHaveBeenCalledWith(code1);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const code1 = { id: 123 };
        spyOn(code1Service, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ code1 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(code1Service.update).toHaveBeenCalledWith(code1);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
