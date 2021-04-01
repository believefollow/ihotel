jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CtClassService } from '../service/ct-class.service';
import { ICtClass, CtClass } from '../ct-class.model';

import { CtClassUpdateComponent } from './ct-class-update.component';

describe('Component Tests', () => {
  describe('CtClass Management Update Component', () => {
    let comp: CtClassUpdateComponent;
    let fixture: ComponentFixture<CtClassUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let ctClassService: CtClassService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CtClassUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CtClassUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CtClassUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      ctClassService = TestBed.inject(CtClassService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const ctClass: ICtClass = { id: 456 };

        activatedRoute.data = of({ ctClass });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(ctClass));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ctClass = { id: 123 };
        spyOn(ctClassService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ctClass });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ctClass }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(ctClassService.update).toHaveBeenCalledWith(ctClass);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ctClass = new CtClass();
        spyOn(ctClassService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ctClass });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ctClass }));
        saveSubject.complete();

        // THEN
        expect(ctClassService.create).toHaveBeenCalledWith(ctClass);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const ctClass = { id: 123 };
        spyOn(ctClassService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ ctClass });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(ctClassService.update).toHaveBeenCalledWith(ctClass);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
